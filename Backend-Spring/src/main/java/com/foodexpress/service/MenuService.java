package com.foodexpress.service;

import com.foodexpress.dto.PiattoDto;
import com.foodexpress.dto.PiattoRequest;
import com.foodexpress.exception.ApiException;
import com.foodexpress.model.Categoria;
import com.foodexpress.model.Piatto;
import com.foodexpress.repository.CategoriaRepository;
import com.foodexpress.repository.PiattoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/** Catalogo del menu e gestione piatti (CRUD + upload foto) — Macro-funzione 1. */
@Service
public class MenuService {

    private final PiattoRepository piattoRepository;
    private final CategoriaRepository categoriaRepository;
    private final String uploadDir;

    public MenuService(PiattoRepository piattoRepository, CategoriaRepository categoriaRepository,
                       @Value("${app.upload.dir:immagini/piatti}") String uploadDir) {
        this.piattoRepository = piattoRepository;
        this.categoriaRepository = categoriaRepository;
        this.uploadDir = uploadDir;
    }

    /** Carica/aggiorna la foto di un piatto e ne imposta l'URL pubblico. */
    public PiattoDto caricaImmagine(Long id, MultipartFile file) {
        Piatto p = trovaPiatto(id);
        if (file == null || file.isEmpty()) {
            throw ApiException.badRequest("Nessun file caricato");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw ApiException.badRequest("Il file deve essere un'immagine");
        }
        try {
            Path dir = Paths.get(uploadDir);
            Files.createDirectories(dir);
            String ext = estensione(file.getOriginalFilename());
            String nomeFile = "piatto-" + id + "-" + System.currentTimeMillis() + ext;
            Files.copy(file.getInputStream(), dir.resolve(nomeFile),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            p.setImmagineUrl("/immagini/piatti/" + nomeFile);
            return PiattoDto.da(piattoRepository.save(p));
        } catch (IOException e) {
            throw ApiException.badRequest("Errore nel salvataggio dell'immagine: " + e.getMessage());
        }
    }

    private String estensione(String nome) {
        if (nome == null) return ".jpg";
        int i = nome.lastIndexOf('.');
        return (i >= 0) ? nome.substring(i).toLowerCase() : ".jpg";
    }

    public List<PiattoDto> menu(Long categoriaId) {
        List<Piatto> piatti = (categoriaId != null)
                ? piattoRepository.findByCategoriaIdAndDisponibileTrue(categoriaId)
                : piattoRepository.findByDisponibileTrue();
        return piatti.stream().map(PiattoDto::da).toList();
    }

    /** Tutti i piatti, compresi i non disponibili (per il pannello ADMIN). */
    public List<PiattoDto> tutti() {
        return piattoRepository.findAll().stream().map(PiattoDto::da).toList();
    }

    public PiattoDto getById(Long id) {
        return PiattoDto.da(trovaPiatto(id));
    }

    public PiattoDto crea(PiattoRequest req) {
        Piatto p = new Piatto();
        applica(p, req);
        return PiattoDto.da(piattoRepository.save(p));
    }

    public PiattoDto aggiorna(Long id, PiattoRequest req) {
        Piatto p = trovaPiatto(id);
        applica(p, req);
        return PiattoDto.da(piattoRepository.save(p));
    }

    public void elimina(Long id) {
        piattoRepository.delete(trovaPiatto(id));
    }

    /** Attiva/disattiva rapidamente la disponibilità di un piatto. */
    public PiattoDto impostaDisponibilita(Long id, boolean disponibile) {
        Piatto p = trovaPiatto(id);
        p.setDisponibile(disponibile);
        return PiattoDto.da(piattoRepository.save(p));
    }

    private void applica(Piatto p, PiattoRequest req) {
        Categoria cat = categoriaRepository.findById(req.categoriaId())
                .orElseThrow(() -> ApiException.notFound("Categoria non trovata: " + req.categoriaId()));
        p.setNome(req.nome());
        p.setDescrizione(req.descrizione());
        p.setPrezzo(req.prezzo());
        p.setCategoria(cat);
        p.setImmagineUrl(req.immagineUrl());
        p.setDisponibile(req.disponibile() == null || req.disponibile());
    }

    private Piatto trovaPiatto(Long id) {
        return piattoRepository.findById(id)
                .orElseThrow(() -> ApiException.notFound("Piatto non trovato: " + id));
    }
}
