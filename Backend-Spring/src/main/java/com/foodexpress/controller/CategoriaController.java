package com.foodexpress.controller;

import com.foodexpress.model.Categoria;
import com.foodexpress.repository.CategoriaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Elenco categorie (pubblico), per i filtri del menu. */
@RestController
@RequestMapping("/api/categorie")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public List<Categoria> elenca() {
        return categoriaRepository.findAll();
    }
}
