package com.sac_gestionale.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sac_gestionale.entity.Cliente;
import com.sac_gestionale.repository.ClienteRepository;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

public Cliente salvaCliente(Cliente nuovoCliente) {
        if (clienteRepository.existsByCodiceFiscale(nuovoCliente.getCodiceFiscale())) {
            throw new IllegalArgumentException("Il Cliente con questo Codice Fiscale è già presente!");
        }
        return clienteRepository.save(nuovoCliente);
    }

    public List<Cliente> trovaTuttiClienti() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> trovaClientePerId(Integer id)  {
        return clienteRepository.findById(id);
    }

    public Cliente aggiornaCliente(Integer id, Cliente clienteAggiornato) {
        clienteAggiornato.setId(id);
        return clienteRepository.save(clienteAggiornato);
    }

    public void eliminaCliente(Integer id) {
        clienteRepository.deleteById(id);
    }

    public Optional<Cliente> trovaClientePerCf(String codiceFiscale)  {
        return clienteRepository.findByCodiceFiscale(codiceFiscale);
    }

    public List<Cliente> ricercaPerNomeECognome(String nome, String cognome) {
        return clienteRepository.findByNomeContainingIgnoreCaseAndCognomeContainingIgnoreCase(nome, cognome);
    }
}
