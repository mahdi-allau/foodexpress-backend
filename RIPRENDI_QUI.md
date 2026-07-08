# FoodExpress — Stato del progetto (riprendi da qui)

Progetto d'esame **Ingegneria del Software** (UniFE 2025/2026): app di **food delivery**
con gamification. Stessa architettura di SmartShop + vincoli d'esame (gamification +
integrazione REST esterna). Autore GitHub: **Mahdi-Allau**.

## Stack e porte
- Backend: **Spring Boot 3.3.5 / Java 17 → porta 8081**
- Frontend: **Angular 18 → porta 4300** (proxy `/api` e `/immagini` verso :8081)
- Database: **MySQL 9.7.1** (locale, in `C:\Users\mahdi\Desktop\mysql-9.7.1-winx64`),
  **porta 3306**, root / `ecoquest123`, schema `foodexpress`
- Percorso progetto: `C:\Users\mahdi\Desktop\ingegneria del software\foodexpress\`

## Come avviare
Doppio clic su **`AVVIA-TUTTO.bat`** (apre 3 finestre: MySQL, backend, frontend) oppure in
VS Code: **Terminal → Esegui attività → "Avvia FoodExpress (tutto)"**.
Ordine: MySQL prima, poi backend, poi frontend. Apri **http://localhost:4300**.
⚠️ L'altro progetto `campusmarket` (Docker) usa anch'esso la **3306**: tienine acceso
**uno alla volta** (se FoodExpress non parte, spegni il container di campusmarket).

## Credenziali demo
- Admin: `admin@foodexpress.it` / `admin123`
- Utente: `user@foodexpress.it` / `user123`
- Voucher: `BENVENUTO10` (10%), `SCONTO5` (5€ sopra 15€), `FOOD20` (20% sopra 25€)

## Cosa è FATTO
- App completa e verificata: menu (54 piatti con foto, 7 categorie), carrello lato client,
  checkout ospite/registrato con **geocoding Nominatim** (distanza/costo/tempo), voucher,
  pagamento carta/contanti, gamification (punti/livelli/badge/classifica), **pannello admin**
  (menu+foto+toggle disponibilità+ricerca, ordini con stati, utenti blocca/elimina,
  impostazioni sito nome/logo/contatti/messaggio home), modifica profilo. Test JUnit verdi.
- **GitHub (pubblici, a nome Mahdi-Allau)**: 
  https://github.com/mahdi-allau/foodexpress-backend e /foodexpress-frontend
  (immagini caricate ESCLUSE dal repo via .gitignore; commit con email noreply
  `201092942+Mahdi-Allau@users.noreply.github.com`).
- **Trello pubblica**: https://trello.com/b/N9uHwykS/foodexpress-ingegneria-del-software
  (colonne TO DO/DOING/DONE/VERIFIED con Story Points). Link in `relazione_esame/LINKS.txt`.
- **Dump DB reale**: `relazione_esame/foodexpress_dump.sql`.
- **Relazione 10 slide**: `relazione_esame/FoodExpress_Relazione.pptx` (Slide 5 ha la board
  Trello disegnata; Slide 4 Miro è facoltativa/vuota).

## Cosa MANCA (da fare dall'utente)
1. Aprire `FoodExpress_Relazione.pptx` in PowerPoint → **File → Esporta → Crea PDF** →
   salvare `FoodExpress_Relazione.pdf` in `relazione_esame/`.
2. Registrare il **video demo** (OBS, ~1-2 min, user journey) → salvare in `relazione_esame/`.
3. Ultimo **commit + push** su GitHub (lo fa l'assistente: token in Windows Credential
   Manager target `git:https://mahdi-allau@github.com`; push con token nell'URL perché
   il credential manager blocca il push normale).

## Gotchas tecnici
- JSON in camelCase (Jackson default); colonne DB snake_case (Hibernate).
- Immagini servite su `/immagini/**`: il `proxy.conf.json` di Angular DEVE proxare anche
  `/immagini` (oltre a `/api`). StaticResourceConfig usa path `file:` NON codificato (spazio
  in "ingegneria del software").
- `styles.css` globale ha `h2 { display: inline-block }` (per la sottolineatura oro): se un
  titolo si "appiccica" a un bottone, aggiungere `display:block` inline a quell'h2.
- Enum MySQL: aggiungere valori a un enum già creato dà "Data truncated" → serve ALTER a
  VARCHAR (già fatto per `ordine.stato`).
- Modifiche non committate al momento: fix `display:block` nei titoli di checkout.component.ts;
  restyle utente di styles.css/menu/leaderboard (intenzionale).
