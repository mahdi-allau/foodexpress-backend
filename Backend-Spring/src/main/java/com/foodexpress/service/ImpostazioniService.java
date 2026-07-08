package com.foodexpress.service;

import com.foodexpress.dto.ImpostazioniRequest;
import com.foodexpress.exception.ApiException;
import com.foodexpress.model.Impostazioni;
import com.foodexpress.repository.ImpostazioniRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/** Gestione delle impostazioni globali del sito (singleton) + upload logo. */
@Service
public class ImpostazioniService {

    private static final String LOGO_DIR = "immagini/sito";

    private final ImpostazioniRepository repository;

    public ImpostazioniService(ImpostazioniRepository repository) {
        this.repository = repository;
    }

    /** Restituisce le impostazioni, creandole con i valori di default se assenti. */
    public Impostazioni get() {
        return repository.findById(1L).orElseGet(() -> {
            Impostazioni i = new Impostazioni();
            i.setIndirizzo("Corso Ercole I d'Este, Ferrara");
            i.setTelefono("0532 000000");
            return repository.save(i);
        });
    }

    public Impostazioni aggiorna(ImpostazioniRequest req) {
        Impostazioni i = get();
        i.setNomeSito(req.nomeSito().trim());
        i.setIndirizzo(req.indirizzo());
        i.setTelefono(req.telefono());
        i.setMessaggioHome(req.messaggioHome());
        return repository.save(i);
    }

    public Impostazioni caricaLogo(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw ApiException.badRequest("Nessun file caricato");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw ApiException.badRequest("Il logo deve essere un'immagine");
        }
        try {
            Path dir = Paths.get(LOGO_DIR);
            Files.createDirectories(dir);
            String nome = file.getOriginalFilename();
            String ext = (nome != null && nome.contains(".")) ? nome.substring(nome.lastIndexOf('.')).toLowerCase() : ".png";
            String nomeFile = "logo-" + System.currentTimeMillis() + ext;
            Files.copy(file.getInputStream(), dir.resolve(nomeFile), StandardCopyOption.REPLACE_EXISTING);
            Impostazioni i = get();
            i.setLogoUrl("/immagini/sito/" + nomeFile);
            return repository.save(i);
        } catch (IOException e) {
            throw ApiException.badRequest("Errore nel salvataggio del logo: " + e.getMessage());
        }
    }
}
