package com.sac_gestionale.repository;

import com.sac_gestionale.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    // Spring crea automaticamente la query SQL: SELECT * FROM utenti WHERE username = ?
    Optional<Utente> findByUsername(String username);
}