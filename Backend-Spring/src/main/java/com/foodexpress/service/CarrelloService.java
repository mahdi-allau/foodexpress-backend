package com.foodexpress.service;

import com.foodexpress.dto.CarrelloDtos.CarrelloDto;
import com.foodexpress.dto.CarrelloDtos.VoceDto;
import com.foodexpress.exception.ApiException;
import com.foodexpress.model.*;
import com.foodexpress.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/** Gestione del carrello dell'utente — parte della Macro-funzione 2. */
@Service
public class CarrelloService {

    private final CarrelloRepository carrelloRepository;
    private final VoceCarrelloRepository voceRepository;
    private final PiattoRepository piattoRepository;

    public CarrelloService(CarrelloRepository carrelloRepository,
                           VoceCarrelloRepository voceRepository,
                           PiattoRepository piattoRepository) {
        this.carrelloRepository = carrelloRepository;
        this.voceRepository = voceRepository;
        this.piattoRepository = piattoRepository;
    }

    /** Recupera (o crea) il carrello dell'utente. */
    @Transactional
    public Carrello getOrCreate(Utente utente) {
        return carrelloRepository.findByUtenteId(utente.getId())
                .orElseGet(() -> carrelloRepository.save(new Carrello(utente)));
    }

    @Transactional
    public CarrelloDto aggiungi(Utente utente, Long piattoId, int quantita) {
        if (quantita < 1) {
            throw ApiException.badRequest("La quantità deve essere almeno 1");
        }
        Piatto piatto = piattoRepository.findById(piattoId)
                .orElseThrow(() -> ApiException.notFound("Piatto non trovato: " + piattoId));
        Carrello carrello = getOrCreate(utente);
        VoceCarrello voce = voceRepository.findByCarrelloIdAndPiattoId(carrello.getId(), piattoId)
                .orElse(new VoceCarrello(carrello, piatto, 0));
        voce.setQuantita(voce.getQuantita() + quantita);
        voceRepository.save(voce);
        return vista(utente);
    }

    @Transactional
    public CarrelloDto rimuovi(Utente utente, Long voceId) {
        Carrello carrello = getOrCreate(utente);
        VoceCarrello voce = voceRepository.findById(voceId)
                .orElseThrow(() -> ApiException.notFound("Voce non trovata"));
        if (!voce.getCarrello().getId().equals(carrello.getId())) {
            throw ApiException.badRequest("La voce non appartiene al tuo carrello");
        }
        voceRepository.delete(voce);
        return vista(utente);
    }

    @Transactional
    public void svuota(Utente utente) {
        Carrello carrello = getOrCreate(utente);
        voceRepository.deleteByCarrelloId(carrello.getId());
    }

    /** Vista del carrello con subtotali e totale. */
    public CarrelloDto vista(Utente utente) {
        Carrello carrello = getOrCreate(utente);
        List<VoceCarrello> voci = voceRepository.findByCarrelloId(carrello.getId());
        BigDecimal totale = BigDecimal.ZERO;
        List<VoceDto> dto = new java.util.ArrayList<>();
        for (VoceCarrello v : voci) {
            BigDecimal subtotale = v.getPiatto().getPrezzo().multiply(BigDecimal.valueOf(v.getQuantita()));
            totale = totale.add(subtotale);
            dto.add(new VoceDto(v.getId(), v.getPiatto().getId(), v.getPiatto().getNome(),
                    v.getPiatto().getPrezzo(), v.getQuantita(), subtotale));
        }
        return new CarrelloDto(dto, totale);
    }
}
