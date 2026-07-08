package com.foodexpress.controller;

import com.foodexpress.dto.AccountUpdateRequest;
import com.foodexpress.dto.AuthDtos.AuthResponse;
import com.foodexpress.service.AccountService;
import com.foodexpress.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Gestione del proprio account (disponibile a qualsiasi utente autenticato). */
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;
    private final UtenteService utenteService;

    public AccountController(AccountService accountService, UtenteService utenteService) {
        this.accountService = accountService;
        this.utenteService = utenteService;
    }

    /** Aggiorna nome/email/password dell'utente autenticato e restituisce un nuovo token. */
    @PutMapping("/profilo")
    public AuthResponse aggiorna(@Valid @RequestBody AccountUpdateRequest req) {
        return accountService.aggiorna(utenteService.getCorrente(), req);
    }
}
