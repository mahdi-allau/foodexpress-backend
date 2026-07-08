package com.foodexpress.controller;

import com.foodexpress.dto.GamificationDtos.BadgeDto;
import com.foodexpress.dto.GamificationDtos.ClassificaDto;
import com.foodexpress.model.Utente;
import com.foodexpress.service.GamificationService;
import com.foodexpress.service.UtenteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/** Gamification: classifica (pubblica), badge e profilo dell'utente. */
@RestController
@RequestMapping("/api")
public class GamificationController {

    private final GamificationService gamificationService;
    private final UtenteService utenteService;

    public GamificationController(GamificationService gamificationService, UtenteService utenteService) {
        this.gamificationService = gamificationService;
        this.utenteService = utenteService;
    }

    @GetMapping("/classifica")
    public List<ClassificaDto> classifica() {
        return gamificationService.classifica();
    }

    @GetMapping("/badge")
    public List<BadgeDto> badge() {
        return gamificationService.badgePerUtente(utenteService.getCorrente().getId());
    }

    @GetMapping("/profilo")
    public Map<String, Object> profilo() {
        Utente u = utenteService.getCorrente();
        return Map.of(
                "id", u.getId(),
                "nome", u.getNome(),
                "email", u.getEmail(),
                "ruolo", u.getRuolo(),
                "puntiFedelta", u.getPuntiFedelta(),
                "livello", u.getLivello(),
                "puntiProssimoLivello", GamificationService.PUNTI_PER_LIVELLO
        );
    }
}
