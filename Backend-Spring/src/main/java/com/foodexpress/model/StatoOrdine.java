package com.foodexpress.model;

/** Stato di avanzamento di un ordine (workflow gestito dall'admin). */
public enum StatoOrdine {
    CREATO,        // appena ricevuto, in attesa di conferma
    ACCETTATO,     // confermato dal ristorante
    RIFIUTATO,     // rifiutato dal ristorante
    IN_CONSEGNA,   // partito, in viaggio verso il cliente
    CONSEGNATO     // consegnato
}
