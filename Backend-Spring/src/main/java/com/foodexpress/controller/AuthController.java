package com.foodexpress.controller;

import com.foodexpress.dto.AuthDtos.AuthResponse;
import com.foodexpress.dto.AuthDtos.LoginRequest;
import com.foodexpress.dto.AuthDtos.RegisterRequest;
import com.foodexpress.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/** Endpoint pubblici di autenticazione. */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest req) {
        return authService.registra(req);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req);
    }
}
