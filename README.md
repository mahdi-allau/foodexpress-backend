# FoodExpress 🍕

Piattaforma di **food delivery** con gamification. L'utente sfoglia il menu,
aggiunge piatti al **carrello**, effettua il **checkout** indicando l'indirizzo
di consegna (di cui il sistema stima **distanza, costo e tempo** via geocoding),
e a ogni ordine guadagna **punti fedeltà**, sale di **livello** e sblocca **badge**;
una **classifica** premia i clienti migliori.

Progetto per il corso di **Ingegneria del Software** — Università di Ferrara, A.A. 2025/2026.
Stessa architettura di SmartShop (catalogo + carrello + ordini + admin + JWT) con in più
i vincoli d'esame: gamification e integrazione REST esterna.

## Stack
- **Frontend:** Angular 18 (standalone, lazy loading) — porta **4300**
- **Backend:** Spring Boot 3.3.5 / Java 17, REST + JWT — porta **8081**
- **Database:** MySQL (schema `foodexpress`)
- **Integrazione REST esterna:** Nominatim / OpenStreetMap (geocoding)

## Architettura
```
Angular (FE) ⇄ REST/JSON ⇄ Spring Boot (MVC: controller→service→repository→model) ⇄ JPA ⇄ MySQL
                                   │
                                   └── WebClient ⇄ Nominatim/OpenStreetMap (REST esterno)
```
Il backend usa **DTO** per trasformare le entità JPA prima di inviarle al frontend.

## Avvio rapido
### 1) Database MySQL (via Docker)
```bash
docker compose up -d
```
MySQL su `localhost:3306`, database `foodexpress`, utente `root` / `ecoquest123`.
In alternativa importa il dump: `mysql -u root -p < relazione_esame/foodexpress_dump.sql`.

### 2) Backend
```bash
cd Backend-Spring
./gradlew bootRun        # Windows: gradlew.bat bootRun
```
→ http://localhost:8081 (al primo avvio popola menu, badge e utenti demo)

### 3) Frontend
```bash
cd Frontend
npm install
npm start                # ng serve, porta 4300, proxy /api -> :8081
```
→ http://localhost:4300

## Credenziali demo
| Ruolo  | Email                  | Password  |
|--------|------------------------|-----------|
| Admin  | admin@foodexpress.it   | admin123  |
| Utente | user@foodexpress.it    | user123   |

## Test
```bash
cd Backend-Spring
./gradlew test           # unit test gamification + smoke test del context (H2)
```

## API principali
| Metodo | Endpoint | Descrizione | Auth |
|--------|----------|-------------|------|
| POST | `/api/auth/register` · `/api/auth/login` | Registrazione / login | pubblico |
| GET  | `/api/menu?categoriaId=` | Menu con filtro | pubblico |
| GET  | `/api/categorie` | Categorie | pubblico |
| GET  | `/api/carrello` · POST · DELETE | Gestione carrello | utente |
| GET  | `/api/ordini/stima?indirizzo=&citta=` | Stima consegna (geocoding) | utente |
| POST | `/api/ordini/checkout` | Checkout → ordine + punti | utente |
| GET  | `/api/ordini/miei` | Storico ordini | utente |
| GET  | `/api/classifica` | Classifica clienti | pubblico |
| GET  | `/api/badge` · `/api/profilo` | Badge / profilo | utente |
| POST/PUT/DELETE | `/api/admin/piatti/**` | Gestione menu | ADMIN |

## Mappatura sugli 11 criteri di valutazione
1. Analisi requisiti (Trello) → `relazione_esame/LINKS.txt`
2. Relazione → `relazione_esame/RELAZIONE_scaletta.md`
3. REST + MVC → controller/service/repository/model + DTO
4. Funzionalità frontend → 8 pagine Angular
5. Funzionalità backend → CRUD piatti, carrello, checkout+geocoding, gamification, classifica
6. Commenti → Javadoc/commenti su classi e metodi
7. Demo → video OBS in `relazione_esame/`
8. Test cases → `GamificationServiceTest` + `contextLoads`
9. Accessibilità/usabilità → HTML semantico, label, focus visibile, skip-link
10. Gestione DB → MySQL, relazioni 1:N e N:M, dump fornito
11. Disegno architetturale → 3 layer separati + REST esterno
