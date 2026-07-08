package com.foodexpress.repository;

import com.foodexpress.model.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrdineRepository extends JpaRepository<Ordine, Long> {
    List<Ordine> findByUtenteIdOrderByDataOrdineDesc(Long utenteId);
    List<Ordine> findAllByOrderByDataOrdineDesc();
    long countByUtenteId(Long utenteId);
}
