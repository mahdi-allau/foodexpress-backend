package com.foodexpress.dto;

import com.foodexpress.model.Badge;
import java.time.LocalDateTime;

/** DTO di gamification (classifica e badge). */
public class GamificationDtos {

    public record ClassificaDto(
            int posizione,
            Long utenteId,
            String nome,
            int puntiFedelta,
            int livello
    ) {
    }

    public record BadgeDto(
            Long id,
            String nome,
            String descrizione,
            int sogliaPunti,
            String icona,
            boolean sbloccato,
            LocalDateTime dataSblocco
    ) {
        public static BadgeDto da(Badge b, boolean sbloccato, LocalDateTime dataSblocco) {
            return new BadgeDto(b.getId(), b.getNome(), b.getDescrizione(),
                    b.getSogliaPunti(), b.getIcona(), sbloccato, dataSblocco);
        }
    }
}
