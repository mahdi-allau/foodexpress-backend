package com.foodexpress.model;

import jakarta.persistence.*;

/** Impostazioni globali del sito (riga singola), gestite dall'admin. */
@Entity
@Table(name = "impostazioni")
public class Impostazioni {

    @Id
    private Long id = 1L; // singleton

    @Column(nullable = false)
    private String nomeSito = "FoodExpress";

    /** URL del logo caricato (null = usa l'icona di default). */
    private String logoUrl;

    private String indirizzo;
    private String telefono;

    /** Messaggio/news pubblico mostrato in home (null/vuoto = nessun banner). */
    @Column(length = 1000)
    private String messaggioHome;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomeSito() { return nomeSito; }
    public void setNomeSito(String nomeSito) { this.nomeSito = nomeSito; }
    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getMessaggioHome() { return messaggioHome; }
    public void setMessaggioHome(String messaggioHome) { this.messaggioHome = messaggioHome; }
}
