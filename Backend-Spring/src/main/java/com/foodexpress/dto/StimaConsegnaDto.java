package com.foodexpress.dto;

import java.math.BigDecimal;

/** Stima di consegna prodotta dal geocoding (integrazione REST esterna). */
public record StimaConsegnaDto(
        double distanzaKm,
        int tempoStimatoMin,
        BigDecimal costoConsegna,
        boolean indirizzoTrovato
) {
}
