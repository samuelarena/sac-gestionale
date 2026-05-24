package com.sac_gestionale.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sac_gestionale.entity.Rata;

@Repository
public interface RataRepository extends JpaRepository<Rata, Long> {

    @Query("SELECT r FROM Rata r JOIN FETCH r.rinnovo rin JOIN FETCH rin.polizza p WHERE r.pagata = false AND r.dataScadenza < :oggi")
    List<Rata> findInsoluti(@Param("oggi") LocalDate oggi);
}