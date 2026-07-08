package com.foodexpress.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** DTO per autenticazione (registrazione, login, risposta col token). */
public class AuthDtos {

    public record RegisterRequest(
            @NotBlank String nome,
            @Email @NotBlank String email,
            @NotBlank @Size(min = 4, message = "La password deve avere almeno 4 caratteri") String password
    ) {
    }

    public record LoginRequest(
            @Email @NotBlank String email,
            @NotBlank String password
    ) {
    }

    public record AuthResponse(
            String token,
            Long utenteId,
            String nome,
            String email,
            String ruolo,
            int puntiFedelta,
            int livello
    ) {
    }
}
