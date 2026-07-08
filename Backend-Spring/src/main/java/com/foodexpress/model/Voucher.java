package com.foodexpress.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/** Voucher/codice sconto applicabile al pagamento. */
@Entity
@Table(name = "voucher")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codice;

    private String descrizione;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSconto tipoSconto;

    /** Valore: percentuale (es. 10 = 10%) oppure importo fisso in euro. */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valore;

    /** Importo minimo dell'ordine (sul subtotale) per poter usare il voucher. */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal minimoOrdine = BigDecimal.ZERO;

    @Column(nullable = false)
    private boolean attivo = true;

    public Voucher() {
    }

    public Voucher(String codice, String descrizione, TipoSconto tipoSconto,
                   BigDecimal valore, BigDecimal minimoOrdine) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.tipoSconto = tipoSconto;
        this.valore = valore;
        this.minimoOrdine = minimoOrdine;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodice() { return codice; }
    public void setCodice(String codice) { this.codice = codice; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public TipoSconto getTipoSconto() { return tipoSconto; }
    public void setTipoSconto(TipoSconto tipoSconto) { this.tipoSconto = tipoSconto; }
    public BigDecimal getValore() { return valore; }
    public void setValore(BigDecimal valore) { this.valore = valore; }
    public BigDecimal getMinimoOrdine() { return minimoOrdine; }
    public void setMinimoOrdine(BigDecimal minimoOrdine) { this.minimoOrdine = minimoOrdine; }
    public boolean isAttivo() { return attivo; }
    public void setAttivo(boolean attivo) { this.attivo = attivo; }
}
