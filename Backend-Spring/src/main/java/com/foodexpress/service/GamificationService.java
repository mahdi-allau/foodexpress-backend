package com.foodexpress.service;

import com.foodexpress.dto.GamificationDtos.BadgeDto;
import com.foodexpress.dto.GamificationDtos.ClassificaDto;
import com.foodexpress.model.Badge;
import com.foodexpress.model.Utente;
import com.foodexpress.model.UtenteBadge;
import com.foodexpress.repository.BadgeRepository;
import com.foodexpress.repository.UtenteBadgeRepository;
import com.foodexpress.repository.UtenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Gamification: punti fedeltà, livelli, sblocco automatico dei badge e classifica.
 */
@Service
public class GamificationService {

    /** Ogni 100 punti fedeltà l'utente sale di un livello. */
    public static final int PUNTI_PER_LIVELLO = 100;

    private final UtenteRepository utenteRepository;
    private final BadgeRepository badgeRepository;
    private final UtenteBadgeRepository utenteBadgeRepository;

    public GamificationService(UtenteRepository utenteRepository,
                               BadgeRepository badgeRepository,
                               UtenteBadgeRepository utenteBadgeRepository) {
        this.utenteRepository = utenteRepository;
        this.badgeRepository = badgeRepository;
        this.utenteBadgeRepository = utenteBadgeRepository;
    }

    /** Aggiunge punti fedeltà, ricalcola il livello e sblocca i nuovi badge. */
    @Transactional
    public List<Badge> assegnaPunti(Utente utente, int puntiGuadagnati) {
        utente.setPuntiFedelta(utente.getPuntiFedelta() + puntiGuadagnati);
        utente.setLivello(calcolaLivello(utente.getPuntiFedelta()));
        utenteRepository.save(utente);
        return sbloccaBadge(utente);
    }

    public int calcolaLivello(int punti) {
        return 1 + (punti / PUNTI_PER_LIVELLO);
    }

    @Transactional
    public List<Badge> sbloccaBadge(Utente utente) {
        List<Badge> nuovi = new ArrayList<>();
        for (Badge b : badgeRepository.findBySogliaPuntiLessThanEqualOrderBySogliaPuntiAsc(utente.getPuntiFedelta())) {
            if (!utenteBadgeRepository.existsByUtenteIdAndBadgeId(utente.getId(), b.getId())) {
                utenteBadgeRepository.save(new UtenteBadge(utente, b));
                nuovi.add(b);
            }
        }
        return nuovi;
    }

    public List<ClassificaDto> classifica() {
        List<ClassificaDto> righe = new ArrayList<>();
        int pos = 1;
        for (Utente u : utenteRepository.findTop10ByOrderByPuntiFedeltaDesc()) {
            righe.add(new ClassificaDto(pos++, u.getId(), u.getNome(), u.getPuntiFedelta(), u.getLivello()));
        }
        return righe;
    }

    public List<BadgeDto> badgePerUtente(Long utenteId) {
        Map<Long, UtenteBadge> sbloccati = utenteBadgeRepository.findByUtenteId(utenteId).stream()
                .collect(Collectors.toMap(ub -> ub.getBadge().getId(), ub -> ub));
        return badgeRepository.findAll().stream()
                .sorted((a, b) -> Integer.compare(a.getSogliaPunti(), b.getSogliaPunti()))
                .map(b -> {
                    UtenteBadge ub = sbloccati.get(b.getId());
                    return BadgeDto.da(b, ub != null, ub != null ? ub.getDataSblocco() : null);
                })
                .toList();
    }
}
