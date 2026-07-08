package com.foodexpress.controller;

import com.foodexpress.dto.OrdineDtos.IndirizzoRequest;
import com.foodexpress.dto.OrdineDtos.OrdineDto;
import com.foodexpress.dto.OrdineDtos.StatoRequest;
import com.foodexpress.service.OrdineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Gestione ordini riservata all'ADMIN: elenco, cambio stato, modifica indirizzo. */
@RestController
@RequestMapping("/api/admin/ordini")
public class AdminOrdineController {

    private final OrdineService ordineService;

    public AdminOrdineController(OrdineService ordineService) {
        this.ordineService = ordineService;
    }

    /** Tutti gli ordini, dal più recente. */
    @GetMapping
    public List<OrdineDto> tutti() {
        return ordineService.tuttiOrdini();
    }

    /** Cambia lo stato dell'ordine (accetta/rifiuta/partito/consegnato). */
    @PutMapping("/{id}/stato")
    public OrdineDto cambiaStato(@PathVariable Long id, @RequestBody StatoRequest req) {
        return ordineService.aggiornaStato(id, req.stato());
    }

    /** Modifica l'indirizzo di consegna (ricalcola distanza/costo/tempo). */
    @PutMapping("/{id}/indirizzo")
    public OrdineDto cambiaIndirizzo(@PathVariable Long id, @RequestBody IndirizzoRequest req) {
        return ordineService.aggiornaIndirizzo(id, req.indirizzo(), req.citta());
    }
}
