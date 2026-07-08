package com.foodexpress.controller;

import com.foodexpress.dto.PiattoDto;
import com.foodexpress.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Menu pubblico: lista piatti (con filtro categoria) e dettaglio. */
@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public List<PiattoDto> menu(@RequestParam(required = false) Long categoriaId) {
        return menuService.menu(categoriaId);
    }

    @GetMapping("/{id}")
    public PiattoDto dettaglio(@PathVariable Long id) {
        return menuService.getById(id);
    }
}
