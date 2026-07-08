package com.foodexpress.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/** Badge sbloccato da un utente (associazione N:M). */
@Entity
@Table(name = "utente_badge",
        uniqueConstraints = @UniqueConstraint(columnNames = {"utente_id", "badge_id"}))
public class UtenteBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "utente_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password"})
    private Utente utente;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "badge_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Badge badge;

    @Column(nullable = false)
    private LocalDateTime dataSblocco = LocalDateTime.now();

    public UtenteBadge() {
    }

    public UtenteBadge(Utente utente, Badge badge) {
        this.utente = utente;
        this.badge = badge;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Utente getUtente() { return utente; }
    public void setUtente(Utente utente) { this.utente = utente; }
    public Badge getBadge() { return badge; }
    public void setBadge(Badge badge) { this.badge = badge; }
    public LocalDateTime getDataSblocco() { return dataSblocco; }
    public void setDataSblocco(LocalDateTime dataSblocco) { this.dataSblocco = dataSblocco; }
}
