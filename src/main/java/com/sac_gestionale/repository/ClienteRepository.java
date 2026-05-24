package com.sac_gestionale.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sac_gestionale.entity.Cliente;
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    public Optional<Cliente> findByCodiceFiscale (String codiceFiscale );
    public boolean existsByCodiceFiscale (String codiceFiscale);

}
