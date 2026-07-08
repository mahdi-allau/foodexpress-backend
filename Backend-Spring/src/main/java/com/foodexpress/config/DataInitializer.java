package com.foodexpress.config;

import com.foodexpress.model.*;
import com.foodexpress.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Popola il database con dati demo al primo avvio (categorie, piatti, badge,
 * utenti). Gli utenti sono creati indipendentemente dai dati di riferimento.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final PiattoRepository piattoRepository;
    private final BadgeRepository badgeRepository;
    private final UtenteRepository utenteRepository;
    private final VoucherRepository voucherRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(CategoriaRepository categoriaRepository,
                           PiattoRepository piattoRepository,
                           BadgeRepository badgeRepository,
                           UtenteRepository utenteRepository,
                           VoucherRepository voucherRepository,
                           PasswordEncoder passwordEncoder) {
        this.categoriaRepository = categoriaRepository;
        this.piattoRepository = piattoRepository;
        this.badgeRepository = badgeRepository;
        this.utenteRepository = utenteRepository;
        this.voucherRepository = voucherRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        seedUtenti();
        seedVoucher();
        if (categoriaRepository.count() > 0) {
            return;
        }

        Categoria antipasti = categoriaRepository.save(new Categoria("Antipasti", "Per iniziare", "🥗"));
        Categoria primi = categoriaRepository.save(new Categoria("Primi", "Pasta e risotti", "🍝"));
        Categoria pizze = categoriaRepository.save(new Categoria("Pizze", "Cotte nel forno a legna", "🍕"));
        Categoria secondi = categoriaRepository.save(new Categoria("Secondi", "Carne e pesce", "🍖"));
        Categoria dolci = categoriaRepository.save(new Categoria("Dolci", "Per concludere", "🍰"));
        Categoria bevande = categoriaRepository.save(new Categoria("Bevande", "Da bere", "🥤"));

        piatto("Bruschette miste", "Pane tostato con pomodoro e basilico", "5.50", antipasti);
        piatto("Tagliere di salumi", "Selezione di salumi e formaggi", "9.00", antipasti);
        piatto("Spaghetti alla Carbonara", "Uovo, guanciale, pecorino", "8.50", primi);
        piatto("Risotto ai funghi", "Riso Carnaroli e funghi porcini", "9.50", primi);
        piatto("Lasagne al forno", "Ragù di carne e besciamella", "9.00", primi);
        piatto("Pizza Margherita", "Pomodoro, mozzarella, basilico", "6.50", pizze);
        piatto("Pizza Diavola", "Pomodoro, mozzarella, salame piccante", "8.00", pizze);
        piatto("Pizza Quattro Formaggi", "Mozzarella, gorgonzola, fontina, parmigiano", "8.50", pizze);
        piatto("Tagliata di manzo", "Con rucola e grana", "14.00", secondi);
        piatto("Branzino al forno", "Con patate e olive", "15.00", secondi);
        piatto("Tiramisù", "Il classico tiramisù della casa", "4.50", dolci);
        piatto("Panna cotta", "Con coulis ai frutti di bosco", "4.00", dolci);
        piatto("Acqua naturale 0.5L", "Bottiglia", "1.50", bevande);
        piatto("Coca-Cola 0.33L", "Lattina", "2.50", bevande);

        badgeRepository.save(new Badge("Buongustaio", "Primi 50 punti fedeltà", 50, "🍴"));
        badgeRepository.save(new Badge("Gourmet", "150 punti fedeltà", 150, "👨‍🍳"));
        badgeRepository.save(new Badge("Chef del Gusto", "300 punti fedeltà", 300, "⭐"));
        badgeRepository.save(new Badge("Leggenda del Gusto", "600 punti fedeltà", 600, "🏆"));
    }

    private void seedVoucher() {
        if (voucherRepository.count() > 0) {
            return;
        }
        voucherRepository.save(new Voucher("BENVENUTO10", "Sconto di benvenuto 10%",
                TipoSconto.PERCENTUALE, new BigDecimal("10"), BigDecimal.ZERO));
        voucherRepository.save(new Voucher("SCONTO5", "5€ di sconto sopra i 15€",
                TipoSconto.FISSO, new BigDecimal("5.00"), new BigDecimal("15.00")));
        voucherRepository.save(new Voucher("FOOD20", "20% di sconto sopra i 25€",
                TipoSconto.PERCENTUALE, new BigDecimal("20"), new BigDecimal("25.00")));
    }

    private void seedUtenti() {
        if (utenteRepository.count() > 0) {
            return;
        }
        utenteRepository.save(new Utente("Admin", "admin@foodexpress.it",
                passwordEncoder.encode("admin123"), "ADMIN"));
        utenteRepository.save(new Utente("Mario Rossi", "user@foodexpress.it",
                passwordEncoder.encode("user123"), "USER"));
    }

    private void piatto(String nome, String descr, String prezzo, Categoria cat) {
        Piatto p = new Piatto();
        p.setNome(nome);
        p.setDescrizione(descr);
        p.setPrezzo(new BigDecimal(prezzo));
        p.setCategoria(cat);
        p.setDisponibile(true);
        piattoRepository.save(p);
    }
}
