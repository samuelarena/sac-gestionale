package com.sac_gestionale.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sac_gestionale.entity.Sinistro;
import com.sac_gestionale.entity.Polizza;
import com.sac_gestionale.repository.SinistroRepository;
import com.sac_gestionale.repository.PolizzaRepository;

@Service
public class SinistroService {

    @Autowired
    private SinistroRepository sinistroRepository;

    @Autowired
    private PolizzaRepository polizzaRepository;

    public Sinistro salvaSinistro(Sinistro nuovoSinistro) {
        // 1. Controllo duplicati sul numero sinistro
        if (sinistroRepository.existsByNumeroSinistro(nuovoSinistro.getNumeroSinistro())) {
            throw new IllegalArgumentException("Il Sinistro con questo numero è già presente a sistema!");
        }

        // 2. Controllo integrità: la polizza associata esiste?
        if (nuovoSinistro.getPolizza() == null || nuovoSinistro.getPolizza().getId() == null) {
            throw new IllegalArgumentException("È necessario associare il sinistro a una Polizza valida.");
        }
        
        Polizza polizzaEsistente = polizzaRepository.findById(nuovoSinistro.getPolizza().getId())
            .orElseThrow(() -> new IllegalArgumentException("Impossibile salvare: la Polizza specificata non esiste nel database."));
        
        nuovoSinistro.setPolizza(polizzaEsistente);

        return sinistroRepository.save(nuovoSinistro);
    }

    public List<Sinistro> trovaTuttiSinistri() {
        return sinistroRepository.findAll();
    }

    public Optional<Sinistro> trovaSinistroPerId(Integer id) {
        return sinistroRepository.findById(id);
    }

    public List<Sinistro> trovaSinistriPerTarga(String targa) {
        return sinistroRepository.findByPolizzaTarga(targa);
    }

    public Sinistro aggiornaSinistro(Integer id, Sinistro sinistroAggiornato) {
        // Verifica che esista prima di aggiornare
        Sinistro esistente = sinistroRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Sinistro non trovato"));
            
        esistente.setDescrizione(sinistroAggiornato.getDescrizione());
        esistente.setStimaDanno(sinistroAggiornato.getStimaDanno());
        esistente.setStatoLavorazione(sinistroAggiornato.getStatoLavorazione());
        // Non aggiorniamo data_sinistro o numero_sinistro perché sono dati storici immutabili
        
        return sinistroRepository.save(esistente);
    }

    public void eliminaSinistro(Integer id) {
        sinistroRepository.deleteById(id);
    }

    public long contaSinistri() {
        return sinistroRepository.count();
    }

    public List<Sinistro> ricercaPerNomeECognomeCliente(String nome, String cognome) {
        return sinistroRepository.findByPolizzaClienteNomeContainingIgnoreCaseAndPolizzaClienteCognomeContainingIgnoreCase(nome, cognome);
    }
}