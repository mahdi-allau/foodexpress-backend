package com.foodexpress.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Riga di un ordine. Conserva uno "snapshot" del nome e del prezzo del piatto
 * al momento dell'ordine (così resta corretto anche se il menu cambia).
 */
@Entity
@Table(name = "voce_ordine")
public class VoceOrdine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ordine_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Ordine ordine;

    @Column(nullable = false)
    private String nomePiatto;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prezzoUnitario;

    @Column(nullable = false)
    private int quantita;

    public VoceOrdine() {
    }

    public VoceOrdine(Ordine ordine, String nomePiatto, BigDecimal prezzoUnitario, int quantita) {
        this.ordine = ordine;
        this.nomePiatto = nomePiatto;
        this.prezzoUnitario = prezzoUnitario;
        this.quantita = quantita;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Ordine getOrdine() { return ordine; }
    public void setOrdine(Ordine ordine) { this.ordine = ordine; }
    public String getNomePiatto() { return nomePiatto; }
    public void setNomePiatto(String nomePiatto) { this.nomePiatto = nomePiatto; }
    public BigDecimal getPrezzoUnitario() { return prezzoUnitario; }
    public void setPrezzoUnitario(BigDecimal prezzoUnitario) { this.prezzoUnitario = prezzoUnitario; }
    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }
}
