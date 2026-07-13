package com.sac_gestionale.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sac_gestionale.entity.Sinistro;

@Repository
public interface SinistroRepository extends JpaRepository<Sinistro, Integer> {
    
    List<Sinistro> findByPolizzaTarga(String targa);
    
    boolean existsByNumeroSinistro(String numeroSinistro);

    List<Sinistro> findByPolizzaClienteNomeContainingIgnoreCaseAndPolizzaClienteCognomeContainingIgnoreCase(String nome, String cognome);
}