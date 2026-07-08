package com.foodexpress.controller;

import com.foodexpress.dto.ImpostazioniRequest;
import com.foodexpress.model.Impostazioni;
import com.foodexpress.service.ImpostazioniService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/** Impostazioni del sito: lettura pubblica, modifica e upload logo riservati all'admin. */
@RestController
public class ImpostazioniController {

    private final ImpostazioniService service;

    public ImpostazioniController(ImpostazioniService service) {
        this.service = service;
    }

    /** Impostazioni pubbliche (nome, logo, contatti, messaggio home). */
    @GetMapping("/api/impostazioni")
    public Impostazioni get() {
        return service.get();
    }

    /** Modifica delle impostazioni (ADMIN). */
    @PutMapping("/api/admin/impostazioni")
    public Impostazioni aggiorna(@Valid @RequestBody ImpostazioniRequest req) {
        return service.aggiorna(req);
    }

    /** Upload del logo del sito (ADMIN, multipart campo "file"). */
    @PostMapping("/api/admin/impostazioni/logo")
    public Impostazioni caricaLogo(@RequestParam("file") MultipartFile file) {
        return service.caricaLogo(file);
    }
}
