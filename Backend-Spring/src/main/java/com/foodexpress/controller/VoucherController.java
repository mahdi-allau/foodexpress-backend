package com.foodexpress.controller;

import com.foodexpress.dto.VoucherDto;
import com.foodexpress.service.VoucherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/** Validazione/anteprima di un voucher al checkout (pubblico). */
@RestController
@RequestMapping("/api/voucher")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    /** Verifica il codice e restituisce lo sconto applicabile sul subtotale indicato. */
    @GetMapping("/valida")
    public VoucherDto valida(@RequestParam String codice, @RequestParam BigDecimal subtotale) {
        VoucherService.Esito e = voucherService.valida(codice, subtotale);
        return new VoucherDto(e.voucher().getCodice(), e.voucher().getDescrizione(), e.sconto());
    }
}
