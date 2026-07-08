package com.foodexpress;

import com.foodexpress.model.Badge;
import com.foodexpress.model.Utente;
import com.foodexpress.repository.BadgeRepository;
import com.foodexpress.repository.UtenteBadgeRepository;
import com.foodexpress.repository.UtenteRepository;
import com.foodexpress.service.GamificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Test della logica di gamification (Test Case del progetto):
 * calcolo del livello dai punti fedeltà e sblocco automatico dei badge
 * al superamento della soglia (es. dopo un ordine).
 */
class GamificationServiceTest {

    private UtenteRepository utenteRepository;
    private BadgeRepository badgeRepository;
    private UtenteBadgeRepository utenteBadgeRepository;
    private GamificationService service;

    @BeforeEach
    void setUp() {
        utenteRepository = Mockito.mock(UtenteRepository.class);
        badgeRepository = Mockito.mock(BadgeRepository.class);
        utenteBadgeRepository = Mockito.mock(UtenteBadgeRepository.class);
        service = new GamificationService(utenteRepository, badgeRepository, utenteBadgeRepository);
        when(utenteRepository.save(any(Utente.class))).thenAnswer(inv -> inv.getArgument(0));
    }

    @Test
    @DisplayName("Il livello è 1 + punti/100")
    void calcoloLivello() {
        assertEquals(1, service.calcolaLivello(0));
        assertEquals(1, service.calcolaLivello(99));
        assertEquals(2, service.calcolaLivello(100));
        assertEquals(4, service.calcolaLivello(300));
    }

    @Test
    @DisplayName("Assegnando punti aumentano i punti fedeltà, si ricalcola il livello e si sblocca il badge")
    void assegnaPuntiSbloccaBadge() {
        Utente u = new Utente("Mario", "mario@test.it", "x", "USER");
        u.setId(1L);
        u.setPuntiFedelta(40); // sotto la soglia "Buongustaio" (50)

        Badge buongustaio = new Badge("Buongustaio", "50 punti", 50, "🍴");
        buongustaio.setId(99L);

        when(badgeRepository.findBySogliaPuntiLessThanEqualOrderBySogliaPuntiAsc(anyInt()))
                .thenReturn(List.of(buongustaio));
        when(utenteBadgeRepository.existsByUtenteIdAndBadgeId(1L, 99L)).thenReturn(false);

        List<Badge> nuovi = service.assegnaPunti(u, 25); // -> 65 punti

        assertEquals(65, u.getPuntiFedelta());
        assertEquals(1, u.getLivello());
        assertEquals(1, nuovi.size());
        assertEquals("Buongustaio", nuovi.get(0).getNome());
        verify(utenteBadgeRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Un badge già posseduto non viene sbloccato di nuovo")
    void nonRiSblocca() {
        Utente u = new Utente("Lucia", "lucia@test.it", "x", "USER");
        u.setId(2L);
        u.setPuntiFedelta(200);

        Badge b = new Badge("Buongustaio", "50 punti", 50, "🍴");
        b.setId(99L);
        when(badgeRepository.findBySogliaPuntiLessThanEqualOrderBySogliaPuntiAsc(anyInt()))
                .thenReturn(List.of(b));
        when(utenteBadgeRepository.existsByUtenteIdAndBadgeId(2L, 99L)).thenReturn(true);

        List<Badge> nuovi = service.assegnaPunti(u, 10);

        assertTrue(nuovi.isEmpty());
        verify(utenteBadgeRepository, never()).save(any());
    }
}
