package com.foodexpress.repository;

import com.foodexpress.model.VoceOrdine;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VoceOrdineRepository extends JpaRepository<VoceOrdine, Long> {
    List<VoceOrdine> findByOrdineId(Long ordineId);
}
