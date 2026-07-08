package com.foodexpress.service;

import com.foodexpress.dto.AuthDtos.AuthResponse;
import com.foodexpress.dto.AuthDtos.LoginRequest;
import com.foodexpress.dto.AuthDtos.RegisterRequest;
import com.foodexpress.exception.ApiException;
import com.foodexpress.model.Utente;
import com.foodexpress.repository.UtenteRepository;
import com.foodexpress.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** Registrazione e login con password cifrata (BCrypt) e token JWT. */
@Service
public class AuthService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse registra(RegisterRequest req) {
        if (utenteRepository.existsByEmail(req.email())) {
            throw ApiException.conflict("Email già registrata");
        }
        Utente u = new Utente(req.nome(), req.email(),
                passwordEncoder.encode(req.password()), "USER");
        u = utenteRepository.save(u);
        return creaRisposta(u);
    }

    public AuthResponse login(LoginRequest req) {
        Utente u = utenteRepository.findByEmail(req.email())
                .orElseThrow(() -> ApiException.unauthorized("Credenziali non valide"));
        if (!passwordEncoder.matches(req.password(), u.getPassword())) {
            throw ApiException.unauthorized("Credenziali non valide");
        }
        if (u.isBloccato()) {
            throw ApiException.unauthorized("Account bloccato. Contatta l'assistenza.");
        }
        return creaRisposta(u);
    }

    private AuthResponse creaRisposta(Utente u) {
        String token = jwtUtil.generaToken(u.getEmail(), u.getRuolo());
        return new AuthResponse(token, u.getId(), u.getNome(), u.getEmail(),
                u.getRuolo(), u.getPuntiFedelta(), u.getLivello());
    }
}
