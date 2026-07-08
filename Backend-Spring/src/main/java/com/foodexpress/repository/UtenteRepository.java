package com.foodexpress.repository;

import com.foodexpress.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByEmail(String email);
    boolean existsByEmail(String email);
    /** Classifica clienti per punti fedeltà. */
    List<Utente> findTop10ByOrderByPuntiFedeltaDesc();
}
