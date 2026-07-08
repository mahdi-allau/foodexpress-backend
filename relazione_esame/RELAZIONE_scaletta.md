# Relazione FoodExpress — scaletta delle 10 slide

> Da trasformare in PowerPoint ed esportare in PDF (come da regole di valutazione).

### Slide 1 — Oggetto del progetto
- **FoodExpress – piattaforma di food delivery con gamification**.
- Idea: ordina piatti, ricevi a casa, guadagna punti fedeltà e badge.
- Stack: Angular 18 · Spring Boot 3 · MySQL · integrazione REST Nominatim (geocoding).

### Slide 2 — Distribuzione del team (ruoli)
- Progetto individuale. Ruoli a rotazione: Team Leader (TO DO) / Developer (DONE) / Tester (VERIFIED).

### Slide 3 — Evidenze delle cerimonie
- Screenshot Trello (TO DO / DONE / VERIFIED) con gli Story Points.

### Slide 4 — Disegno funzionale (da Miro)
- 3 macro-funzioni: Menu · Carrello&Ordini · Gamification.
- Entità: Utente, Categoria, Piatto, Carrello, VoceCarrello, Ordine, VoceOrdine, Badge, UtenteBadge.

### Slide 5 — Screenshot di Trello
- Task con Story Points (totale ~34 SP), carico bilanciato.

### Slide 6 — Test cases
- `GamificationServiceTest` (JUnit + Mockito): livello dai punti, sblocco badge, no doppio sblocco.
- `FoodexpressApplicationTests.contextLoads` (context su H2).
- Screenshot di `BUILD SUCCESSFUL`.

### Slide 7 — Disegno architetturale
- 3 layer: **Angular** ⇄ REST/JSON ⇄ **Spring Boot (MVC)** ⇄ JPA ⇄ **MySQL**.
- DTO per la trasformazione dati BE→FE. JWT stateless.
- Integrazione REST esterna: WebClient → Nominatim per il geocoding dell'indirizzo.

### Slide 8 — Funzionalità implementate (elenco)
- Registrazione/Login (JWT). Menu con filtri. Carrello (aggiungi/rimuovi/svuota).
- Checkout con stima di consegna (geocoding). Storico ordini.
- Punti fedeltà, livelli, badge automatici, classifica. Pannello ADMIN piatti.

### Slide 9 — Dettaglio di una funzionalità: "Checkout con stima di consegna"
- L'utente inserisce l'indirizzo → `GET /api/ordini/stima` chiama Nominatim, calcola la
  distanza dal ristorante (haversine) → costo e tempo di consegna.
- `POST /api/ordini/checkout`: crea l'ordine (snapshot voci), svuota il carrello,
  assegna i punti fedeltà e sblocca i badge → risposta `CheckoutResponse`.
- Inserire snippet di `OrdineService.checkout` + `GeocodingService` e screenshot dell'app.

### Slide 10 — Feedback dell'esperienza
- Cosa ho imparato (REST, MVC, carrello/ordini, integrazione servizio esterno, gamification).
- Difficoltà e soluzioni. Sviluppi futuri (pagamenti, tracking live, recensioni).

---

## Demo video
Registrare con OBS lo *user journey*: registrazione → menu → aggiungi al carrello →
checkout con stima consegna → ordine confermato (punti + badge) → ordini → classifica.
Salvare il file in questa cartella `relazione_esame/`.
