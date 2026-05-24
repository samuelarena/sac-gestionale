package com.sac_gestionale.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sac_gestionale.entity.Polizza;

@Repository
public interface PolizzaRepository extends JpaRepository<Polizza, Integer> {
    
    // Somma i premi solo dei rinnovi attualmente attivi (non storicizzati) di polizze valide
    @Query("SELECT SUM(r.premio) FROM Polizza p JOIN p.rinnovi r WHERE p.cancellato = false AND r.attivo = true")
    BigDecimal calcolaTotaleIncassi();

    // Metodi per i controlli di unicità e recupero
    boolean existsByNumeroPolizza(String numeroPolizza);
    
    Optional<Polizza> findByNumeroPolizza(String numeroPolizza);

    @Query("SELECT p FROM Polizza p JOIN p.rinnovi r WHERE p.cancellato = false AND r.attivo = true AND r.dataScadenza BETWEEN :oggi AND :dataLimite")
    List<Polizza> findByScadenzaPolizze (@Param("oggi") LocalDate oggi, @Param("dataLimite") LocalDate dataLimite);
}
