package com.foodexpress.dto;

import java.math.BigDecimal;
import java.util.List;

/** DTO relativi al carrello. */
public class CarrelloDtos {

    /** Richiesta di aggiunta di un piatto al carrello. */
    public record AggiungiRequest(Long piattoId, Integer quantita) {
    }

    /** Riga del carrello con subtotale calcolato. */
    public record VoceDto(
            Long id,
            Long piattoId,
            String nomePiatto,
            BigDecimal prezzo,
            int quantita,
            BigDecimal subtotale
    ) {
    }

    /** Carrello completo con totale. */
    public record CarrelloDto(
            List<VoceDto> voci,
            BigDecimal totale
    ) {
    }
}
