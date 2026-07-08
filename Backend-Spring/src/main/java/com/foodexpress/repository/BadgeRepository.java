package com.foodexpress.repository;

import com.foodexpress.model.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    List<Badge> findBySogliaPuntiLessThanEqualOrderBySogliaPuntiAsc(int punti);
}
