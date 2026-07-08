package com.foodexpress.dto;

import java.math.BigDecimal;

/** Risultato della validazione di un voucher (per l'anteprima al checkout). */
public record VoucherDto(
        String codice,
        String descrizione,
        BigDecimal sconto
) {
}
