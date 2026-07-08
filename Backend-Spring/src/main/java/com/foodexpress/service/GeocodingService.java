package com.foodexpress.service;

import com.foodexpress.dto.StimaConsegnaDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * Macro-funzione 2 — Integrazione REST con un applicativo esterno (Nominatim /
 * OpenStreetMap). Converte l'indirizzo di consegna in coordinate, calcola la
 * distanza dal ristorante (formula dell'emisenoverso) e ne stima costo e tempo
 * di consegna. Se il servizio non è raggiungibile usa una distanza di fallback.
 */
@Service
public class GeocodingService {

    private static final BigDecimal TARIFFA_BASE = new BigDecimal("2.50");
    private static final BigDecimal TARIFFA_PER_KM = new BigDecimal("0.80");
    private static final double DISTANZA_FALLBACK_KM = 3.0;

    private final WebClient webClient;
    private final String userAgent;
    private final double ristoranteLat;
    private final double ristoranteLon;

    public GeocodingService(WebClient.Builder builder,
                            @Value("${app.geocoding.base-url}") String baseUrl,
                            @Value("${app.geocoding.user-agent}") String userAgent,
                            @Value("${app.restaurant.lat}") double ristoranteLat,
                            @Value("${app.restaurant.lon}") double ristoranteLon) {
        this.webClient = builder.baseUrl(baseUrl).build();
        this.userAgent = userAgent;
        this.ristoranteLat = ristoranteLat;
        this.ristoranteLon = ristoranteLon;
    }

    /** Stima la consegna per l'indirizzo indicato. */
    public StimaConsegnaDto stima(String indirizzo, String citta) {
        double[] coord = geocodifica(indirizzo + ", " + citta);
        boolean trovato = coord != null;
        double distanza = trovato
                ? haversineKm(ristoranteLat, ristoranteLon, coord[0], coord[1])
                : DISTANZA_FALLBACK_KM;
        distanza = arrotonda(distanza);
        int tempo = 15 + (int) Math.round(distanza * 4); // 15 min base + 4 min/km
        BigDecimal costo = TARIFFA_BASE.add(TARIFFA_PER_KM.multiply(BigDecimal.valueOf(distanza)))
                .setScale(2, RoundingMode.HALF_UP);
        return new StimaConsegnaDto(distanza, tempo, costo, trovato);
    }

    /** Chiamata REST a Nominatim: indirizzo -> [lat, lon] (null se non trovato). */
    @SuppressWarnings("unchecked")
    private double[] geocodifica(String query) {
        try {
            List<Map<String, Object>> risultati = webClient.get()
                    .uri(uri -> uri.path("/search")
                            .queryParam("q", query)
                            .queryParam("format", "json")
                            .queryParam("limit", 1)
                            .build())
                    .header("User-Agent", userAgent)
                    .retrieve()
                    .bodyToMono(List.class)
                    .timeout(Duration.ofSeconds(6))
                    .block();

            if (risultati == null || risultati.isEmpty()) {
                return null;
            }
            Map<String, Object> primo = risultati.get(0);
            double lat = Double.parseDouble(String.valueOf(primo.get("lat")));
            double lon = Double.parseDouble(String.valueOf(primo.get("lon")));
            return new double[]{lat, lon};
        } catch (Exception e) {
            return null; // fallback gestito dal chiamante
        }
    }

    /** Distanza in km tra due punti (formula dell'emisenoverso / haversine). */
    private double haversineKm(double lat1, double lon1, double lat2, double lon2) {
        double r = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return r * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    private double arrotonda(double v) {
        return BigDecimal.valueOf(v).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
