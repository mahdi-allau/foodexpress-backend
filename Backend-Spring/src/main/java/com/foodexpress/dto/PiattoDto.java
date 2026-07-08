package com.foodexpress.dto;

import com.foodexpress.model.Piatto;
import java.math.BigDecimal;

/**
 * Vista appiattita di un Piatto per il frontend: i dati della categoria
 * (relazione N:1) diventano campi diretti. Esempio di trasformazione dati BE→FE.
 */
public record PiattoDto(
        Long id,
        String nome,
        String descrizione,
        BigDecimal prezzo,
        Long categoriaId,
        String categoriaNome,
        String categoriaIcona,
        String immagineUrl,
        boolean disponibile
) {
    public static PiattoDto da(Piatto p) {
        return new PiattoDto(
                p.getId(), p.getNome(), p.getDescrizione(), p.getPrezzo(),
                p.getCategoria() != null ? p.getCategoria().getId() : null,
                p.getCategoria() != null ? p.getCategoria().getNome() : null,
                p.getCategoria() != null ? p.getCategoria().getIcona() : null,
                p.getImmagineUrl(), p.isDisponibile());
    }
}
