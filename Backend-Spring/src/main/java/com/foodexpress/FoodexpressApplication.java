package com.foodexpress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto di ingresso di FoodExpress: piattaforma di food delivery con catalogo,
 * carrello, ordini, gamification (punti fedeltà, livelli, badge, classifica) e
 * integrazione REST esterna per il geocoding dell'indirizzo di consegna.
 */
@SpringBootApplication
public class FoodexpressApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoodexpressApplication.class, args);
    }
}
