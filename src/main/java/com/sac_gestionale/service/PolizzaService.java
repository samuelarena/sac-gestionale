package com.sac_gestionale.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.sac_gestionale.entity.Polizza;
import com.sac_gestionale.entity.Rinnovo;
import com.sac_gestionale.repository.PolizzaRepository;

@Service
public class PolizzaService {

    @Autowired
    private PolizzaRepository polizzaRepository;

    // --- FLUSSO 1: CREAZIONE PRIMA EMISSIONE ---
    public Polizza salvaPolizza(Polizza new_polizza) {
        // 1. Controllo di unicità per evitare l'eccezione 500 del database
        if (polizzaRepository.existsByNumeroPolizza(new_polizza.getNumeroPolizza())) {
            throw new IllegalArgumentException("Una polizza con il numero " + new_polizza.getNumeroPolizza() + " esiste già a sistema.");
        }

        // 2. Assicurati che i rinnovi passati nel JSON siano legati correttamente al padre
        if (new_polizza.getRinnovi() != null) {
            for (Rinnovo rinnovo : new_polizza.getRinnovi()) {
                rinnovo.setPolizza(new_polizza);
                rinnovo.setAttivo(true); // Imposta il primo rinnovo come quello attualmente valido
            }
        }

        return polizzaRepository.save(new_polizza);
    }

    // --- FLUSSO 2: REGISTRAZIONE RINNOVO ANNUALE ---
    public Polizza aggiungiRinnovo(String numeroPolizza, Rinnovo nuovoRinnovo) {
        // 1. Recupera la polizza padre dal numero
        Polizza polizza = polizzaRepository.findByNumeroPolizza(numeroPolizza)
                .orElseThrow(() -> new IllegalArgumentException("Nessuna polizza trovata con numero: " + numeroPolizza));

        // 2. Storicizza tutti i vecchi rinnovi
        if (polizza.getRinnovi() != null) {
            for (Rinnovo r : polizza.getRinnovi()) {
                r.setAttivo(false);
            }
        }

        // 3. Associa il nuovo rinnovo (che viene impostato automaticamente ad attivo)
        nuovoRinnovo.setAttivo(true);
        polizza.addRinnovo(nuovoRinnovo);

        // 4. Salva il padre, Hibernate gestirà l'UPDATE sui vecchi e l'INSERT sul nuovo in cascata
        return polizzaRepository.save(polizza);
    }

    // --- OPERAZIONI CRUD STANDARD ---
    public Page<Polizza> trovaTuttePolizze(int nPage, int size) {
        Pageable page = PageRequest.of(nPage, size);
        return polizzaRepository.findAll(page);
    }

    public Optional<Polizza> trovaPolizzaPerId(Integer id) {
        return polizzaRepository.findById(id);
    }

    public Polizza aggiornaPolizza(Integer id, Polizza polizzaAggiornata) {
        Polizza esistente = polizzaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Polizza non trovata"));

        // Aggiorna solo i dati anagrafici intoccando lo storico rinnovi
        esistente.setTarga(polizzaAggiornata.getTarga());
        esistente.setTipoRamo(polizzaAggiornata.getTipoRamo());
        // Aggiungi qui altri campi base se necessario (es. cliente_id)
        
        return polizzaRepository.save(esistente);
    }

    public void eliminaPolizza(Integer id) {
        polizzaRepository.deleteById(id);
    }

    public long contaPolizze() {
        return polizzaRepository.count();
    }

    public BigDecimal calcolaTotaleIncassi() {
        return polizzaRepository.calcolaTotaleIncassi();
    }

    public Optional<Polizza> trovaPolizzaPerNumero (String numeroPolizza) {
        return polizzaRepository.findByNumeroPolizza(numeroPolizza);
    }

    public List<Polizza> trovaPolizzeInScadenza(int giorni) { // Passiamo i giorni come parametro
        LocalDate oggi = LocalDate.now();
        LocalDate limite = oggi.plusDays(giorni);
        return polizzaRepository.findByScadenzaPolizze(oggi, limite);
    }

    public List<Polizza> ricercaPerNomeECognomeCliente(String nome, String cognome) {
        return polizzaRepository.findByClienteNomeContainingIgnoreCaseAndClienteCognomeContainingIgnoreCase(nome, cognome);
    }
}