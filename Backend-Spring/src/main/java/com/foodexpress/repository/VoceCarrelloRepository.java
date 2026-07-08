package com.foodexpress.repository;

import com.foodexpress.model.VoceCarrello;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VoceCarrelloRepository extends JpaRepository<VoceCarrello, Long> {
    List<VoceCarrello> findByCarrelloId(Long carrelloId);
    Optional<VoceCarrello> findByCarrelloIdAndPiattoId(Long carrelloId, Long piattoId);
    void deleteByCarrelloId(Long carrelloId);
}
