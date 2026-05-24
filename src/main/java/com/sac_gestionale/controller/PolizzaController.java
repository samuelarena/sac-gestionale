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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PutMapping;

import com.sac_gestionale.entity.Polizza;
import com.sac_gestionale.entity.Rinnovo;
import com.sac_gestionale.service.PolizzaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/polizze")
public class PolizzaController {

    @Autowired
    private PolizzaService polizzaService;

    @PostMapping
    public Polizza creaNuovaPolizza(@Valid @RequestBody Polizza polizza) {
        return polizzaService.salvaPolizza(polizza);
    }

    @PostMapping("/{numeroPolizza}/rinnovi")
    public Polizza aggiungiRinnovoAnnuale(@PathVariable String numeroPolizza, @Valid @RequestBody Rinnovo rinnovo) {
        return polizzaService.aggiungiRinnovo(numeroPolizza, rinnovo);
    }

    // --- LETTURE E RICERCHE ---
    @GetMapping
    public Page<Polizza> getTuttiPolizza(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return polizzaService.trovaTuttePolizze(page, size);
    }

    @GetMapping("/{id}")
    public Optional<Polizza> getPolizzaSingola(@PathVariable Integer id) {
        return polizzaService.trovaPolizzaPerId(id);
    }

    // --- AGGIORNAMENTO DATI BASE ---
    @PutMapping("/{id}")
    public Polizza updatePolizza(@PathVariable Integer id, @Valid @RequestBody Polizza polizza) {
        return polizzaService.aggiornaPolizza(id, polizza);
    }

    // --- ELIMINAZIONE LOGICA ---
    @DeleteMapping("/{id}")
    public void deletePolizza(@PathVariable Integer id) {
        polizzaService.eliminaPolizza(id);
    }

    @GetMapping("/numero/{numeroPolizza}")
    public Optional<Polizza> getPolizzaPerNumero(@PathVariable String numeroPolizza) {
        return polizzaService.trovaPolizzaPerNumero(numeroPolizza);
    }
    
    @GetMapping("/in-scadenza")
    public List<Polizza> getPolizzeInScadenza(@RequestParam(defaultValue = "30") int giorni) {
        return polizzaService.trovaPolizzeInScadenza(giorni);
    }
    
}