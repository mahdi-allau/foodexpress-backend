package com.foodexpress.dto;

import jakarta.validation.constraints.NotBlank;

/** Dati modificabili delle impostazioni del sito (lato admin). */
public record ImpostazioniRequest(
        @NotBlank String nomeSito,
        String indirizzo,
        String telefono,
        String messaggioHome
) {
}
