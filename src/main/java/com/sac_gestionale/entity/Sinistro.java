package com.sac_gestionale.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "sinistri")
@SQLDelete(sql = "UPDATE sinistri SET cancellato = true WHERE id = ?")
@SQLRestriction("cancellato = false")
public class Sinistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(name = "numero_sinistro", unique = true, nullable = false)
    private String numeroSinistro;

    private String descrizione;

    @PastOrPresent
    @Column(name = "data_sinistro", nullable = false)
    private LocalDate dataSinistro;

    @Positive
    @Column(name = "danno_stimato", precision = 10, scale = 2)
    private BigDecimal dannoStimato;

    @Column(name = "stato_pratica")
    private String statoPratica = "APERTO";

    @ManyToOne
    @JoinColumn(name = "polizza_id", nullable = false)
    @JsonBackReference
    private Polizza polizza;

    @Column(columnDefinition = "boolean default false")
    private Boolean cancellato = false;

    public Sinistro() {
    }

    // --- GETTER E SETTER ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNumeroSinistro() { return numeroSinistro; }
    public void setNumeroSinistro(String numeroSinistro) {
        if (numeroSinistro != null) {
            this.numeroSinistro = numeroSinistro.toUpperCase().replace(" ", "");
        } else {
            this.numeroSinistro = null;
        }
    }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public LocalDate getDataSinistro() { return dataSinistro; }
    public void setDataSinistro(LocalDate dataSinistro) { this.dataSinistro = dataSinistro; }

    public BigDecimal getDannoStimato() { return dannoStimato; }
    public void setDannoStimato(BigDecimal dannoStimato) { this.dannoStimato = dannoStimato; }

    public String getStatoPratica() { return statoPratica; }
    public void setStatoPratica(String statoPratica) { this.statoPratica = statoPratica; }

    public Polizza getPolizza() { return polizza; }
    public void setPolizza(Polizza polizza) { this.polizza = polizza; }

    public Boolean isCancellato() { return cancellato; }
    public void setCancellato(Boolean cancellato) { this.cancellato = cancellato; }
}