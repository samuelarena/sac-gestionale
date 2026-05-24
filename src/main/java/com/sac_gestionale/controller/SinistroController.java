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
import org.springframework.web.bind.annotation.PutMapping;

import com.sac_gestionale.entity.Sinistro;
import com.sac_gestionale.service.SinistroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sinistri")
public class SinistroController {

    @Autowired
    private SinistroService sinistroService;
    
    @PostMapping
    public Sinistro creaNuovoSinistro(@Valid @RequestBody Sinistro sinistro) {
        return sinistroService.salvaSinistro(sinistro);
    }

    @GetMapping
    public List<Sinistro> getTuttiSinistri() {
        return sinistroService.trovaTuttiSinistri();
    }

    @GetMapping("/{id}")
    public Optional<Sinistro> getSinistroSingolo(@PathVariable Integer id) {
        return sinistroService.trovaSinistroPerId(id);
    }
    
    @GetMapping("/targa/{targa}")
    public List<Sinistro> getSinistriPerTarga(@PathVariable String targa) {
        return sinistroService.trovaSinistriPerTarga(targa);
    }
    
    @PutMapping("/{id}")
    public Sinistro updateSinistro(@PathVariable Integer id, @Valid @RequestBody Sinistro sinistro) {
        return sinistroService.aggiornaSinistro(id, sinistro);
    }

    @DeleteMapping("/{id}")
    public void deleteSinistro(@PathVariable Integer id) {
        sinistroService.eliminaSinistro(id);
    }
}