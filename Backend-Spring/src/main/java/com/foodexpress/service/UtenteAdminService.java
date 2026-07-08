package com.foodexpress.service;

import com.foodexpress.dto.UtenteDto;
import com.foodexpress.exception.ApiException;
import com.foodexpress.model.Carrello;
import com.foodexpress.model.Ordine;
import com.foodexpress.model.Utente;
import com.foodexpress.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/** Gestione utenti lato ADMIN: elenco, blocco/sblocco, eliminazione. */
@Service
public class UtenteAdminService {

    private final UtenteRepository utenteRepository;
    private final OrdineRepository ordineRepository;
    private final CarrelloRepository carrelloRepository;
    private final VoceCarrelloRepository voceCarrelloRepository;
    private final UtenteBadgeRepository utenteBadgeRepository;

    public UtenteAdminService(UtenteRepository utenteRepository, OrdineRepository ordineRepository,
                              CarrelloRepository carrelloRepository, VoceCarrelloRepository voceCarrelloRepository,
                              UtenteBadgeRepository utenteBadgeRepository) {
        this.utenteRepository = utenteRepository;
        this.ordineRepository = ordineRepository;
        this.carrelloRepository = carrelloRepository;
        this.voceCarrelloRepository = voceCarrelloRepository;
        this.utenteBadgeRepository = utenteBadgeRepository;
    }

    public List<UtenteDto> lista() {
        return utenteRepository.findAll().stream()
                .map(u -> UtenteDto.da(u, ordineRepository.countByUtenteId(u.getId())))
                .toList();
    }

    public UtenteDto blocca(Long id, boolean bloccato) {
        Utente u = trova(id);
        if ("ADMIN".equals(u.getRuolo())) {
            throw ApiException.badRequest("Non puoi bloccare un account amministratore");
        }
        u.setBloccato(bloccato);
        utenteRepository.save(u);
        return UtenteDto.da(u, ordineRepository.countByUtenteId(u.getId()));
    }

    /** Elimina l'utente. Gli ordini restano (anonimizzati), carrello e badge vengono rimossi. */
    @Transactional
    public void elimina(Long id) {
        Utente u = trova(id);
        if ("ADMIN".equals(u.getRuolo())) {
            throw ApiException.badRequest("Non puoi eliminare un account amministratore");
        }
        // Anonimizza gli ordini (mantiene lo storico come "ospite")
        for (Ordine o : ordineRepository.findByUtenteIdOrderByDataOrdineDesc(id)) {
            o.setUtente(null);
            ordineRepository.save(o);
        }
        // Rimuovi carrello + voci
        Optional<Carrello> carrello = carrelloRepository.findByUtenteId(id);
        carrello.ifPresent(c -> {
            voceCarrelloRepository.deleteByCarrelloId(c.getId());
            carrelloRepository.delete(c);
        });
        // Rimuovi i badge dell'utente
        utenteBadgeRepository.deleteByUtenteId(id);
        // Elimina l'utente
        utenteRepository.delete(u);
    }

    private Utente trova(Long id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> ApiException.notFound("Utente non trovato: " + id));
    }
}
