package com.foodexpress.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/** Dati in ingresso per creare/aggiornare un piatto (lato ADMIN). */
public record PiattoRequest(
        @NotBlank String nome,
        String descrizione,
        @NotNull @Positive BigDecimal prezzo,
        @NotNull Long categoriaId,
        String immagineUrl,
        Boolean disponibile
) {
}
