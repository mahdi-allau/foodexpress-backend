package com.foodexpress.model;

import jakarta.persistence.*;

/** Categoria di un piatto (es. Antipasti, Primi, Pizze, Dolci, Bevande). 1:N verso Piatto. */
@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(length = 500)
    private String descrizione;

    private String icona;

    public Categoria() {
    }

    public Categoria(String nome, String descrizione, String icona) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.icona = icona;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public String getIcona() { return icona; }
    public void setIcona(String icona) { this.icona = icona; }
}
