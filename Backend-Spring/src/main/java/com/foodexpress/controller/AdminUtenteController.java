package com.foodexpress.controller;

import com.foodexpress.dto.OrdineDtos.OrdineDto;
import com.foodexpress.dto.UtenteDto;
import com.foodexpress.service.OrdineService;
import com.foodexpress.service.UtenteAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Gestione utenti riservata all'ADMIN. */
@RestController
@RequestMapping("/api/admin/utenti")
public class AdminUtenteController {

    private final UtenteAdminService utenteAdminService;
    private final OrdineService ordineService;

    public AdminUtenteController(UtenteAdminService utenteAdminService, OrdineService ordineService) {
        this.utenteAdminService = utenteAdminService;
        this.ordineService = ordineService;
    }

    /** Elenco utenti con numero di ordini. */
    @GetMapping
    public List<UtenteDto> lista() {
        return utenteAdminService.lista();
    }

    /** Ordini di uno specifico utente. */
    @GetMapping("/{id}/ordini")
    public List<OrdineDto> ordini(@PathVariable Long id) {
        return ordineService.mieiOrdini(id);
    }

    /** Blocca o sblocca un utente. */
    @PutMapping("/{id}/blocca")
    public UtenteDto blocca(@PathVariable Long id, @RequestParam boolean bloccato) {
        return utenteAdminService.blocca(id, bloccato);
    }

    /** Elimina un utente (gli ordini restano anonimizzati). */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void elimina(@PathVariable Long id) {
        utenteAdminService.elimina(id);
    }
}
