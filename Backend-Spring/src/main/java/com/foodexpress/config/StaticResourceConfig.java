package com.foodexpress.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

/** Espone le immagini caricate (foto dei piatti) come risorse statiche su /immagini/**. */
@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve l'intera cartella "immagini" (sottocartelle: piatti, sito).
        // Percorso file: NON codificato (gli spazi nel path resterebbero %20 con toUri()).
        String abs = Paths.get("immagini").toAbsolutePath().toString().replace("\\", "/");
        registry.addResourceHandler("/immagini/**")
                .addResourceLocations("file:" + abs + "/");
    }
}
