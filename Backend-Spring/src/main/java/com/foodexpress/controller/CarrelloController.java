package com.foodexpress.controller;

import com.foodexpress.dto.CarrelloDtos.AggiungiRequest;
import com.foodexpress.dto.CarrelloDtos.CarrelloDto;
import com.foodexpress.model.Utente;
import com.foodexpress.service.CarrelloService;
import com.foodexpress.service.UtenteService;
import org.springframework.web.bind.annotation.*;

/** Carrello dell'utente autenticato. */
@RestController
@RequestMapping("/api/carrello")
public class CarrelloController {

    private final CarrelloService carrelloService;
    private final UtenteService utenteService;

    public CarrelloController(CarrelloService carrelloService, UtenteService utenteService) {
        this.carrelloService = carrelloService;
        this.utenteService = utenteService;
    }

    @GetMapping
    public CarrelloDto vista() {
        return carrelloService.vista(utenteService.getCorrente());
    }

    @PostMapping
    public CarrelloDto aggiungi(@RequestBody AggiungiRequest req) {
        Utente u = utenteService.getCorrente();
        int q = req.quantita() == null ? 1 : req.quantita();
        return carrelloService.aggiungi(u, req.piattoId(), q);
    }

    @DeleteMapping("/voce/{voceId}")
    public CarrelloDto rimuovi(@PathVariable Long voceId) {
        return carrelloService.rimuovi(utenteService.getCorrente(), voceId);
    }

    @DeleteMapping
    public void svuota() {
        carrelloService.svuota(utenteService.getCorrente());
    }
}
