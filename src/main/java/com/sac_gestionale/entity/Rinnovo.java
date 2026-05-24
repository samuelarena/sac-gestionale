package com.sac_gestionale.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Transient;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "rinnovi")
public class Rinnovo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    @ManyToOne
    @JoinColumn(name = "polizza_id", nullable = false)
    @JsonBackReference
    private Polizza polizza;

    @NotNull
    @Positive // Il premio deve essere un numero positivo
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal premio;

    @NotNull
    @Column(name = "data_stipula", nullable = false)
    private LocalDate dataStipula;

    @NotNull
    @Column(name = "data_scadenza", nullable = false)
    private LocalDate dataScadenza;

    @Transient
    private Long giorniMancanti;

    @Column(columnDefinition = "boolean default true")
    private Boolean attivo = true;

    @OneToMany(mappedBy = "rinnovo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rata> rate = new ArrayList<>();

    // Costruttore vuoto obbligatorio per JPA
    public Rinnovo() {
    }

    // --- GETTER E SETTER ---

public Long getId() {
    return id;
}

public void setId(Long id) {
        this.id = id;
    }

    public Polizza getPolizza() {
        return polizza;
    }

    public void setPolizza(Polizza polizza) {
        this.polizza = polizza;
    }

    public BigDecimal getPremio() {
        return premio;
    }

    public void setPremio(BigDecimal premio) {
        this.premio = premio;
    }

    public LocalDate getDataStipula() {
        return dataStipula;
    }

    public void setDataStipula(LocalDate dataStipula) {
        this.dataStipula = dataStipula;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public Long getGiorniMancanti() {
        if (this.dataScadenza != null) {
            return ChronoUnit.DAYS.between(LocalDate.now(), this.dataScadenza);
        }
        return null; // o return 0L; se preferisci non avere valori nulli nel JSON
    }

    public Boolean getAttivo() {
        return attivo;
    }

    public void setAttivo(Boolean attivo) {
        this.attivo = attivo;
    }
    public List<Rata> getRate() {
        return rate;
    }

    public void setRate(List<Rata> rate) {
        this.rate = rate;
    }
    
    public void addRata(Rata rata) {
        rate.add(rata);
        rata.setRinnovo(this);
    }

    public void removeRata(Rata rata) {
        rate.remove(rata);
        rata.setRinnovo(null);
    }
}