package com.foodexpress.service;

import com.foodexpress.dto.OrdineDtos.CheckoutRequest;
import com.foodexpress.dto.OrdineDtos.CheckoutResponse;
import com.foodexpress.dto.OrdineDtos.ItemRequest;
import com.foodexpress.dto.OrdineDtos.OrdineDto;
import com.foodexpress.dto.StimaConsegnaDto;
import com.foodexpress.exception.ApiException;
import com.foodexpress.model.*;
import com.foodexpress.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Checkout (carrello lato client) -> Ordine, con stima di consegna via geocoding
 * esterno. Se l'utente è autenticato vengono assegnati i punti fedeltà
 * (gamification); l'ordine può essere effettuato anche come ospite.
 */
@Service
public class OrdineService {

    private final OrdineRepository ordineRepository;
    private final VoceOrdineRepository voceOrdineRepository;
    private final PiattoRepository piattoRepository;
    private final GeocodingService geocodingService;
    private final GamificationService gamificationService;
    private final VoucherService voucherService;

    public OrdineService(OrdineRepository ordineRepository,
                         VoceOrdineRepository voceOrdineRepository,
                         PiattoRepository piattoRepository,
                         GeocodingService geocodingService,
                         GamificationService gamificationService,
                         VoucherService voucherService) {
        this.ordineRepository = ordineRepository;
        this.voceOrdineRepository = voceOrdineRepository;
        this.piattoRepository = piattoRepository;
        this.geocodingService = geocodingService;
        this.gamificationService = gamificationService;
        this.voucherService = voucherService;
    }

    @Transactional
    public CheckoutResponse checkout(CheckoutRequest req, Optional<Utente> utenteOpt) {
        if (req.items() == null || req.items().isEmpty()) {
            throw ApiException.badRequest("Il carrello è vuoto");
        }
        if (req.indirizzo() == null || req.indirizzo().isBlank()
                || req.citta() == null || req.citta().isBlank()) {
            throw ApiException.badRequest("Indirizzo e città di consegna sono obbligatori");
        }

        // Nome cliente: dall'utente registrato oppure dal form (ospite)
        String nomeCliente = utenteOpt.map(Utente::getNome)
                .orElseGet(() -> req.nomeCliente() == null ? "" : req.nomeCliente().trim());
        if (nomeCliente.isBlank()) {
            throw ApiException.badRequest("Indica un nome per la consegna");
        }

        // Crea l'ordine (intestazione)
        Ordine ordine = new Ordine();
        utenteOpt.ifPresent(ordine::setUtente);
        ordine.setNomeCliente(nomeCliente);
        ordine.setIndirizzoConsegna(req.indirizzo());
        ordine.setCittaConsegna(req.citta());

        // Stima di consegna dall'integrazione REST esterna (geocoding)
        StimaConsegnaDto stima = geocodingService.stima(req.indirizzo(), req.citta());
        ordine.setDistanzaKm(stima.distanzaKm());
        ordine.setTempoStimatoMin(stima.tempoStimatoMin());

        // Subtotale + righe ordine (snapshot dei piatti)
        BigDecimal subtotale = BigDecimal.ZERO;
        ordine.setSubtotale(BigDecimal.ZERO);
        ordine.setCostoConsegna(stima.costoConsegna());
        ordine.setTotale(BigDecimal.ZERO);
        ordine = ordineRepository.save(ordine);

        for (ItemRequest item : req.items()) {
            int q = item.quantita() == null ? 1 : item.quantita();
            if (q < 1) {
                throw ApiException.badRequest("Quantità non valida");
            }
            Piatto piatto = piattoRepository.findById(item.piattoId())
                    .orElseThrow(() -> ApiException.notFound("Piatto non trovato: " + item.piattoId()));
            subtotale = subtotale.add(piatto.getPrezzo().multiply(BigDecimal.valueOf(q)));
            voceOrdineRepository.save(new VoceOrdine(ordine, piatto.getNome(), piatto.getPrezzo(), q));
        }

        // Voucher (sconto sul subtotale), se presente
        BigDecimal sconto = BigDecimal.ZERO;
        if (req.codiceVoucher() != null && !req.codiceVoucher().isBlank()) {
            VoucherService.Esito esito = voucherService.valida(req.codiceVoucher(), subtotale);
            sconto = esito.sconto();
            ordine.setCodiceVoucher(esito.voucher().getCodice());
        }

        BigDecimal totale = subtotale.subtract(sconto).add(stima.costoConsegna());
        int punti = utenteOpt.isPresent() ? totale.intValue() : 0; // 1 punto per euro, solo se registrato
        ordine.setSubtotale(subtotale);
        ordine.setSconto(sconto);
        ordine.setTotale(totale);
        ordine.setMetodoPagamento(req.metodoPagamento());
        ordine.setPuntiGuadagnati(punti);
        ordineRepository.save(ordine);

        // Gamification: punti e badge solo per gli utenti registrati
        List<String> nuoviBadge = List.of();
        int puntiFedelta = 0, livello = 0;
        if (utenteOpt.isPresent()) {
            Utente u = utenteOpt.get();
            nuoviBadge = gamificationService.assegnaPunti(u, punti).stream().map(Badge::getNome).toList();
            puntiFedelta = u.getPuntiFedelta();
            livello = u.getLivello();
        }

        OrdineDto dto = OrdineDto.da(ordine, voceOrdineRepository.findByOrdineId(ordine.getId()));
        return new CheckoutResponse(dto, utenteOpt.isEmpty(), puntiFedelta, livello, nuoviBadge);
    }

    public List<OrdineDto> mieiOrdini(Long utenteId) {
        return ordineRepository.findByUtenteIdOrderByDataOrdineDesc(utenteId).stream()
                .map(o -> OrdineDto.da(o, voceOrdineRepository.findByOrdineId(o.getId())))
                .toList();
    }

    // ===== Gestione ordini (ADMIN) =====

    /** Tutti gli ordini, dal più recente (per il pannello admin). */
    public List<OrdineDto> tuttiOrdini() {
        return ordineRepository.findAllByOrderByDataOrdineDesc().stream()
                .map(o -> OrdineDto.da(o, voceOrdineRepository.findByOrdineId(o.getId())))
                .toList();
    }

    /** Aggiorna lo stato di un ordine rispettando le transizioni consentite. */
    @Transactional
    public OrdineDto aggiornaStato(Long ordineId, StatoOrdine nuovo) {
        Ordine o = trova(ordineId);
        if (!transizioneValida(o.getStato(), nuovo)) {
            throw ApiException.badRequest("Transizione di stato non valida: "
                    + o.getStato() + " -> " + nuovo);
        }
        o.setStato(nuovo);
        ordineRepository.save(o);
        return OrdineDto.da(o, voceOrdineRepository.findByOrdineId(o.getId()));
    }

    /** Modifica l'indirizzo di consegna: ri-geocoding e ricalcolo costo/tempo/totale. */
    @Transactional
    public OrdineDto aggiornaIndirizzo(Long ordineId, String indirizzo, String citta) {
        if (indirizzo == null || indirizzo.isBlank() || citta == null || citta.isBlank()) {
            throw ApiException.badRequest("Indirizzo e città sono obbligatori");
        }
        Ordine o = trova(ordineId);
        StimaConsegnaDto stima = geocodingService.stima(indirizzo, citta);
        o.setIndirizzoConsegna(indirizzo);
        o.setCittaConsegna(citta);
        o.setDistanzaKm(stima.distanzaKm());
        o.setTempoStimatoMin(stima.tempoStimatoMin());
        o.setCostoConsegna(stima.costoConsegna());
        BigDecimal sconto = o.getSconto() == null ? BigDecimal.ZERO : o.getSconto();
        o.setTotale(o.getSubtotale().subtract(sconto).add(stima.costoConsegna()));
        ordineRepository.save(o);
        return OrdineDto.da(o, voceOrdineRepository.findByOrdineId(o.getId()));
    }

    private boolean transizioneValida(StatoOrdine da, StatoOrdine a) {
        return switch (da) {
            case CREATO -> a == StatoOrdine.ACCETTATO || a == StatoOrdine.RIFIUTATO;
            case ACCETTATO -> a == StatoOrdine.IN_CONSEGNA || a == StatoOrdine.RIFIUTATO;
            case IN_CONSEGNA -> a == StatoOrdine.CONSEGNATO;
            case RIFIUTATO, CONSEGNATO -> false; // stati finali
        };
    }

    private Ordine trova(Long id) {
        return ordineRepository.findById(id)
                .orElseThrow(() -> ApiException.notFound("Ordine non trovato: " + id));
    }
}
