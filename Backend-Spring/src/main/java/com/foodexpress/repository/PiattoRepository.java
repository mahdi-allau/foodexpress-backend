package com.foodexpress.repository;

import com.foodexpress.model.Piatto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PiattoRepository extends JpaRepository<Piatto, Long> {
    List<Piatto> findByDisponibileTrue();
    List<Piatto> findByCategoriaIdAndDisponibileTrue(Long categoriaId);
}
