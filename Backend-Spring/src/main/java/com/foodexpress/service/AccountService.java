package com.foodexpress.service;

import com.foodexpress.dto.AccountUpdateRequest;
import com.foodexpress.dto.AuthDtos.AuthResponse;
import com.foodexpress.exception.ApiException;
import com.foodexpress.model.Utente;
import com.foodexpress.repository.UtenteRepository;
import com.foodexpress.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** Modifica dei dati del proprio account (nome, email, password). */
@Service
public class AccountService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AccountService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Aggiorna i dati dell'utente. Se l'email cambia viene emesso un nuovo token
     * (il subject del JWT è l'email), così la sessione resta valida.
     */
    public AuthResponse aggiorna(Utente utente, AccountUpdateRequest req) {
        String nuovaEmail = req.email().trim();
        if (!nuovaEmail.equalsIgnoreCase(utente.getEmail())
                && utenteRepository.existsByEmail(nuovaEmail)) {
            throw ApiException.conflict("Email già in uso");
        }
        utente.setNome(req.nome().trim());
        utente.setEmail(nuovaEmail);
        if (req.password() != null && !req.password().isBlank()) {
            if (req.password().length() < 4) {
                throw ApiException.badRequest("La password deve avere almeno 4 caratteri");
            }
            utente.setPassword(passwordEncoder.encode(req.password()));
        }
        utenteRepository.save(utente);

        String token = jwtUtil.generaToken(utente.getEmail(), utente.getRuolo());
        return new AuthResponse(token, utente.getId(), utente.getNome(), utente.getEmail(),
                utente.getRuolo(), utente.getPuntiFedelta(), utente.getLivello());
    }
}
