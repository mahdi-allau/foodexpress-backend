package com.foodexpress.service;

import com.foodexpress.exception.ApiException;
import com.foodexpress.model.TipoSconto;
import com.foodexpress.model.Voucher;
import com.foodexpress.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Validazione dei voucher e calcolo dello sconto sull'imponibile (subtotale). */
@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    /** Esito della validazione di un voucher. */
    public record Esito(Voucher voucher, BigDecimal sconto) {
    }

    /**
     * Valida il codice e calcola lo sconto applicabile sul subtotale.
     * Lancia ApiException (400) se il voucher non è valido o l'ordine è sotto il minimo.
     */
    public Esito valida(String codice, BigDecimal subtotale) {
        if (codice == null || codice.isBlank()) {
            throw ApiException.badRequest("Inserisci un codice voucher");
        }
        Voucher v = voucherRepository.findByCodiceIgnoreCaseAndAttivoTrue(codice.trim())
                .orElseThrow(() -> ApiException.badRequest("Voucher non valido o scaduto"));
        if (subtotale.compareTo(v.getMinimoOrdine()) < 0) {
            throw ApiException.badRequest("Il voucher richiede un ordine minimo di "
                    + v.getMinimoOrdine() + " €");
        }
        BigDecimal sconto;
        if (v.getTipoSconto() == TipoSconto.PERCENTUALE) {
            sconto = subtotale.multiply(v.getValore())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        } else {
            sconto = v.getValore();
        }
        // Lo sconto non può superare il subtotale
        if (sconto.compareTo(subtotale) > 0) {
            sconto = subtotale;
        }
        return new Esito(v, sconto.setScale(2, RoundingMode.HALF_UP));
    }
}
