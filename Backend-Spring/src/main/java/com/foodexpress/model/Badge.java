package com.foodexpress.model;

import jakarta.persistence.*;

/** Badge di gamification, sbloccato al superamento di una soglia di punti fedeltà. */
@Entity
@Table(name = "badge")
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(length = 500)
    private String descrizione;

    @Column(nullable = false)
    private int sogliaPunti;

    private String icona;

    public Badge() {
    }

    public Badge(String nome, String descrizione, int sogliaPunti, String icona) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.sogliaPunti = sogliaPunti;
        this.icona = icona;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public int getSogliaPunti() { return sogliaPunti; }
    public void setSogliaPunti(int sogliaPunti) { this.sogliaPunti = sogliaPunti; }
    public String getIcona() { return icona; }
    public void setIcona(String icona) { this.icona = icona; }
}
