package com.foodexpress.dto;

import com.foodexpress.model.Ordine;
import com.foodexpress.model.StatoOrdine;
import com.foodexpress.model.VoceOrdine;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/** DTO relativi agli ordini. */
public class OrdineDtos {

    /** Singola riga del checkout (piatto + quantità). */
    public record ItemRequest(Long piattoId, Integer quantita) {
    }

    /** Aggiornamento dello stato di un ordine (admin). */
    public record StatoRequest(com.foodexpress.model.StatoOrdine stato) {
    }

    /** Aggiornamento dell'indirizzo di consegna di un ordine (admin). */
    public record IndirizzoRequest(String indirizzo, String citta) {
    }

    /**
     * Richiesta di checkout. Contiene i piatti scelti (carrello lato client) e
     * l'indirizzo di consegna. nomeCliente è usato per gli ordini ospite.
     */
    public record CheckoutRequest(
            List<ItemRequest> items,
            String indirizzo,
            String citta,
            String nomeCliente,
            com.foodexpress.model.MetodoPagamento metodoPagamento,
            String codiceVoucher
    ) {
    }

    public record VoceOrdineDto(String nomePiatto, BigDecimal prezzoUnitario, int quantita) {
        public static VoceOrdineDto da(VoceOrdine v) {
            return new VoceOrdineDto(v.getNomePiatto(), v.getPrezzoUnitario(), v.getQuantita());
        }
    }

    public record OrdineDto(
            Long id,
            String nomeCliente,
            boolean ospite,
            LocalDateTime dataOrdine,
            StatoOrdine stato,
            String indirizzoConsegna,
            String cittaConsegna,
            double distanzaKm,
            int tempoStimatoMin,
            BigDecimal subtotale,
            BigDecimal costoConsegna,
            BigDecimal sconto,
            BigDecimal totale,
            com.foodexpress.model.MetodoPagamento metodoPagamento,
            String codiceVoucher,
            int puntiGuadagnati,
            List<VoceOrdineDto> voci
    ) {
        public static OrdineDto da(Ordine o, List<VoceOrdine> voci) {
            return new OrdineDto(
                    o.getId(), o.getNomeCliente(), o.getUtente() == null,
                    o.getDataOrdine(), o.getStato(),
                    o.getIndirizzoConsegna(), o.getCittaConsegna(),
                    o.getDistanzaKm(), o.getTempoStimatoMin(),
                    o.getSubtotale(), o.getCostoConsegna(), o.getSconto(), o.getTotale(),
                    o.getMetodoPagamento(), o.getCodiceVoucher(),
                    o.getPuntiGuadagnati(),
                    voci.stream().map(VoceOrdineDto::da).toList());
        }
    }

    /** Esito del checkout: ordine + feedback gamificato (solo per utenti registrati). */
    public record CheckoutResponse(
            OrdineDto ordine,
            boolean ospite,
            int puntiFedelta,
            int livello,
            List<String> nuoviBadge
    ) {
    }
}
