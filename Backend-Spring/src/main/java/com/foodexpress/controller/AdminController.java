package com.foodexpress.controller;

import com.foodexpress.dto.PiattoDto;
import com.foodexpress.dto.PiattoRequest;
import com.foodexpress.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/** Gestione piatti riservata all'ADMIN. */
@RestController
@RequestMapping("/api/admin/piatti")
public class AdminController {

    private final MenuService menuService;

    public AdminController(MenuService menuService) {
        this.menuService = menuService;
    }

    /** Tutti i piatti (anche non disponibili). */
    @GetMapping
    public List<PiattoDto> tutti() {
        return menuService.tutti();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PiattoDto crea(@Valid @RequestBody PiattoRequest req) {
        return menuService.crea(req);
    }

    @PutMapping("/{id}")
    public PiattoDto aggiorna(@PathVariable Long id, @Valid @RequestBody PiattoRequest req) {
        return menuService.aggiorna(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void elimina(@PathVariable Long id) {
        menuService.elimina(id);
    }

    /** Carica la foto di un piatto (multipart/form-data, campo "file"). */
    @PostMapping("/{id}/immagine")
    public PiattoDto caricaImmagine(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return menuService.caricaImmagine(id, file);
    }

    /** Attiva/disattiva la disponibilità di un piatto. */
    @PutMapping("/{id}/disponibilita")
    public PiattoDto disponibilita(@PathVariable Long id, @RequestParam boolean disponibile) {
        return menuService.impostaDisponibilita(id, disponibile);
    }
}
