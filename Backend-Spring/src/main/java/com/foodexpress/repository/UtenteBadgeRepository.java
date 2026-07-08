package com.foodexpress.repository;

import com.foodexpress.model.UtenteBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UtenteBadgeRepository extends JpaRepository<UtenteBadge, Long> {
    List<UtenteBadge> findByUtenteId(Long utenteId);
    boolean existsByUtenteIdAndBadgeId(Long utenteId, Long badgeId);
    void deleteByUtenteId(Long utenteId);
}
