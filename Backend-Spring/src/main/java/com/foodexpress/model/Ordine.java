package com.foodexpress.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Ordine creato dal checkout del carrello. Memorizza l'indirizzo di consegna e
 * i dati stimati dal geocoding (distanza, costo e tempo di consegna).
 */
@Entity
@Table(name = "ordine")
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Cliente registrato (null per gli ordini effettuati come ospite). */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "utente_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password"})
    private Utente utente;

    /** Nome del cliente (utile soprattutto per gli ordini ospite). */
    @Column(nullable = false)
    private String nomeCliente;

    @Column(nullable = false)
    private LocalDateTime dataOrdine = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoOrdine stato = StatoOrdine.CREATO;

    // Indirizzo di consegna
    @Column(nullable = false)
    private String indirizzoConsegna;

    @Column(nullable = false)
    private String cittaConsegna;

    // Dati calcolati dal geocoding (integrazione REST esterna)
    private double distanzaKm;
    private int tempoStimatoMin;

    @Column(precision = 10, scale = 2)
    private BigDecimal subtotale = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal costoConsegna = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal sconto = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal totale = BigDecimal.ZERO;

    /** Metodo di pagamento (CARTA o CONTANTI). */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private MetodoPagamento metodoPagamento;

    /** Codice voucher applicato (se presente). */
    private String codiceVoucher;

    private int puntiGuadagnati;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Utente getUtente() { return utente; }
    public void setUtente(Utente utente) { this.utente = utente; }
    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
    public LocalDateTime getDataOrdine() { return dataOrdine; }
    public void setDataOrdine(LocalDateTime dataOrdine) { this.dataOrdine = dataOrdine; }
    public StatoOrdine getStato() { return stato; }
    public void setStato(StatoOrdine stato) { this.stato = stato; }
    public String getIndirizzoConsegna() { return indirizzoConsegna; }
    public void setIndirizzoConsegna(String indirizzoConsegna) { this.indirizzoConsegna = indirizzoConsegna; }
    public String getCittaConsegna() { return cittaConsegna; }
    public void setCittaConsegna(String cittaConsegna) { this.cittaConsegna = cittaConsegna; }
    public double getDistanzaKm() { return distanzaKm; }
    public void setDistanzaKm(double distanzaKm) { this.distanzaKm = distanzaKm; }
    public int getTempoStimatoMin() { return tempoStimatoMin; }
    public void setTempoStimatoMin(int tempoStimatoMin) { this.tempoStimatoMin = tempoStimatoMin; }
    public BigDecimal getSubtotale() { return subtotale; }
    public void setSubtotale(BigDecimal subtotale) { this.subtotale = subtotale; }
    public BigDecimal getCostoConsegna() { return costoConsegna; }
    public void setCostoConsegna(BigDecimal costoConsegna) { this.costoConsegna = costoConsegna; }
    public BigDecimal getSconto() { return sconto; }
    public void setSconto(BigDecimal sconto) { this.sconto = sconto; }
    public BigDecimal getTotale() { return totale; }
    public void setTotale(BigDecimal totale) { this.totale = totale; }
    public MetodoPagamento getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(MetodoPagamento metodoPagamento) { this.metodoPagamento = metodoPagamento; }
    public String getCodiceVoucher() { return codiceVoucher; }
    public void setCodiceVoucher(String codiceVoucher) { this.codiceVoucher = codiceVoucher; }
    public int getPuntiGuadagnati() { return puntiGuadagnati; }
    public void setPuntiGuadagnati(int puntiGuadagnati) { this.puntiGuadagnati = puntiGuadagnati; }
}
