package com.foodexpress.model;

import jakarta.persistence.*;

/**
 * Utente/cliente. Accumula punti fedeltà a ogni ordine consegnato e
 * di conseguenza sale di livello (gamification).
 */
@Entity
@Table(name = "utente")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    /** USER oppure ADMIN. */
    @Column(nullable = false)
    private String ruolo = "USER";

    /** Punti fedeltà totali. */
    @Column(nullable = false)
    private int puntiFedelta = 0;

    /** Livello, derivato dai punti fedeltà. */
    @Column(nullable = false)
    private int livello = 1;

    /** Se true, l'utente è bloccato e non può accedere. */
    @Column(nullable = false)
    private boolean bloccato = false;

    public Utente() {
    }

    public Utente(String nome, String email, String password, String ruolo) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }
    public int getPuntiFedelta() { return puntiFedelta; }
    public void setPuntiFedelta(int puntiFedelta) { this.puntiFedelta = puntiFedelta; }
    public int getLivello() { return livello; }
    public void setLivello(int livello) { this.livello = livello; }
    public boolean isBloccato() { return bloccato; }
    public void setBloccato(boolean bloccato) { this.bloccato = bloccato; }
}
