package com.foodexpress.controller;

import com.foodexpress.dto.OrdineDtos.CheckoutRequest;
import com.foodexpress.dto.OrdineDtos.CheckoutResponse;
import com.foodexpress.dto.OrdineDtos.OrdineDto;
import com.foodexpress.dto.StimaConsegnaDto;
import com.foodexpress.model.Utente;
import com.foodexpress.service.GeocodingService;
import com.foodexpress.service.OrdineService;
import com.foodexpress.service.UtenteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Ordini dell'utente: checkout, storico e stima di consegna (geocoding). */
@RestController
@RequestMapping("/api/ordini")
public class OrdineController {

    private final OrdineService ordineService;
    private final GeocodingService geocodingService;
    private final UtenteService utenteService;

    public OrdineController(OrdineService ordineService, GeocodingService geocodingService,
                            UtenteService utenteService) {
        this.ordineService = ordineService;
        this.geocodingService = geocodingService;
        this.utenteService = utenteService;
    }

    /** Anteprima della stima di consegna (distanza/costo/tempo) prima del checkout. */
    @GetMapping("/stima")
    public StimaConsegnaDto stima(@RequestParam String indirizzo, @RequestParam String citta) {
        return geocodingService.stima(indirizzo, citta);
    }

    /**
     * Checkout: crea l'ordine. Funziona anche come ospite (senza login); se
     * l'utente è autenticato vengono assegnati i punti fedeltà.
     */
    @PostMapping("/checkout")
    public CheckoutResponse checkout(@RequestBody CheckoutRequest req) {
        return ordineService.checkout(req, utenteService.getCorrenteOpt());
    }

    /** Storico ordini dell'utente. */
    @GetMapping("/miei")
    public List<OrdineDto> miei() {
        return ordineService.mieiOrdini(utenteService.getCorrente().getId());
    }
}
