package com.foodexpress.dto;

import com.foodexpress.model.Utente;

/** Vista di un utente per il pannello admin. */
public record UtenteDto(
        Long id,
        String nome,
        String email,
        String ruolo,
        int puntiFedelta,
        int livello,
        boolean bloccato,
        long numeroOrdini
) {
    public static UtenteDto da(Utente u, long numeroOrdini) {
        return new UtenteDto(u.getId(), u.getNome(), u.getEmail(), u.getRuolo(),
                u.getPuntiFedelta(), u.getLivello(), u.isBloccato(), numeroOrdini);
    }
}
