package com.foodexpress.service;

import com.foodexpress.exception.ApiException;
import com.foodexpress.model.Utente;
import com.foodexpress.repository.UtenteRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/** Recupero dell'utente autenticato dal token JWT. */
@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;

    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    public Utente getCorrente() {
        return getCorrenteOpt()
                .orElseThrow(() -> ApiException.unauthorized("Utente non autenticato"));
    }

    /** Utente autenticato se presente, altrimenti vuoto (per i flussi "ospite"). */
    public Optional<Utente> getCorrenteOpt() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null || "anonymousUser".equals(auth.getName())) {
            return Optional.empty();
        }
        return utenteRepository.findByEmail(auth.getName());
    }
}
