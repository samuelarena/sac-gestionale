package com.sac_gestionale.repository;

import com.sac_gestionale.entity.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, Integer> {
    // Spring crea automaticamente la query SQL: SELECT * FROM ruoli WHERE name = ?
    Optional<Ruolo> findByName(String name);
}