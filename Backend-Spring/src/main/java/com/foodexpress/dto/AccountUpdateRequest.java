package com.foodexpress.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Aggiornamento dei dati dell'account. La password è opzionale:
 * se vuota/null, resta invariata.
 */
public record AccountUpdateRequest(
        @NotBlank String nome,
        @Email @NotBlank String email,
        String password
) {
}
