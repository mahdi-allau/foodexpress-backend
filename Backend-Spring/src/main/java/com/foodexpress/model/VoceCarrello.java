package com.foodexpress.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/** Riga del carrello: un piatto con la relativa quantità. */
@Entity
@Table(name = "voce_carrello",
        uniqueConstraints = @UniqueConstraint(columnNames = {"carrello_id", "piatto_id"}))
public class VoceCarrello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "carrello_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Carrello carrello;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "piatto_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Piatto piatto;

    @Column(nullable = false)
    private int quantita = 1;

    public VoceCarrello() {
    }

    public VoceCarrello(Carrello carrello, Piatto piatto, int quantita) {
        this.carrello = carrello;
        this.piatto = piatto;
        this.quantita = quantita;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Carrello getCarrello() { return carrello; }
    public void setCarrello(Carrello carrello) { this.carrello = carrello; }
    public Piatto getPiatto() { return piatto; }
    public void setPiatto(Piatto piatto) { this.piatto = piatto; }
    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }
}
