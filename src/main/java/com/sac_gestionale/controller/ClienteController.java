package com.sac_gestionale.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.sac_gestionale.dto.ClienteSummaryDTO;
import com.sac_gestionale.entity.Cliente;
import com.sac_gestionale.mapper.ClienteMapper;
import com.sac_gestionale.service.ClienteService;
import org.springframework.web.bind.annotation.PutMapping;
import jakarta.validation.Valid;




@RestController
@RequestMapping("/api/clienti")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    @PostMapping
    public Cliente creaNuovoCliente(@Valid @RequestBody Cliente cliente) {
        return clienteService.salvaCliente(cliente);
    }

// --- LETTURE E RICERCHE ---

    // 1. Lista generale: restituisce i DTO mascherati (Summary)
    @GetMapping
    public List<ClienteSummaryDTO> getTuttiClienti() {
        return clienteService.trovaTuttiClienti()
                .stream()
                .map(ClienteMapper::toSummaryDTO)
                .toList();
    }

// 2. Dettaglio singolo: Restituisce un DTO diverso in base a chi fa la richiesta
    @GetMapping("/{id}")
    public ResponseEntity<Object> getClienteSingolo(@PathVariable Integer id, Authentication authentication) {
        
        // 1. Recuperiamo l'entità dal DB (o lanciamo un'eccezione se non esiste)
        Cliente clienteTrovato = clienteService.trovaClientePerId(id)
                .orElseThrow(() -> new IllegalArgumentException("Nessun cliente trovato con ID: " + id));
        
        // 2. Indaghiamo sul ruolo: l'utente loggato è un ADMIN?
        // Usiamo gli Stream di Java per cercare "ROLE_ADMIN" tra i suoi permessi
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(ruolo -> ruolo.equals("ROLE_ADMIN"));

        // 3. Il bivio logico
        if (isAdmin) {
            // È il capo: serviamo il Dettaglio in chiaro (Restituisce ClienteDettaglioDTO)
            return ResponseEntity.ok(ClienteMapper.toDettaglioDTO(clienteTrovato));
        } else {
            // È un operatore: serviamo il Summary mascherato (Restituisce ClienteSummaryDTO)
            return ResponseEntity.ok(ClienteMapper.toSummaryDTO(clienteTrovato));
        }
    }
    
    @PutMapping("/{id}")
    public Cliente updateCliente(@PathVariable Integer id, @Valid @RequestBody Cliente cliente) {
        return clienteService.aggiornaCliente(id, cliente);
    }

    @DeleteMapping("/{id}")
    public void deleteCliente(@PathVariable Integer id) {
        clienteService.eliminaCliente(id);
    }
    
    @GetMapping("/cf/{codiceFiscale}")
    public Optional<Cliente> getMethodName(@PathVariable String codiceFiscale) {
        return clienteService.trovaClientePerCf(codiceFiscale);
    }
    
    @GetMapping("/ricerca")
    public List<ClienteSummaryDTO> ricercaPerNomeECognome(@RequestParam String nome, @RequestParam String cognome) {
        return clienteService.ricercaPerNomeECognome(nome, cognome)
                .stream()
                .map(ClienteMapper::toSummaryDTO)
                .toList();
    }
}
