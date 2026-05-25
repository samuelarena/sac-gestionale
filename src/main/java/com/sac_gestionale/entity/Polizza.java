package com.sac_gestionale.entity;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "polizze")
@SQLDelete(sql = "UPDATE polizze SET cancellato = true WHERE id = ?")
@SQLRestriction("cancellato = false")
public class Polizza extends Auditable {
    @Id // Dichiara che questo campo è la Chiave Primaria (PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Column(unique = true)
    private String numeroPolizza;
    private String tipoRamo;
    private BigDecimal premioAnnuale;
    private LocalDate dataScadenza;
    private String stato = "Attiva";
    @NotBlank
    @Size(min = 7, max = 7)
    @Pattern(regexp = "^[A-Z]{2}[0-9]{3}[A-Z]{2}$")
    private String targa;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonBackReference
    private Cliente cliente;
    @OneToMany(mappedBy = "polizza", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Rinnovo> rinnovi;
    @OneToMany(mappedBy = "polizza")
    @JsonManagedReference
    private List<Sinistro> sinistri;
    @Column(columnDefinition = "boolean default false")
    private Boolean cancellato = false;

    public Polizza() {
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNumeroPolizza() {
        return numeroPolizza;
    }
    public void setNumeroPolizza(String numeroPolizza) {
        this.numeroPolizza = numeroPolizza;
    }
    public String getTipoRamo() {
        return tipoRamo;
    }
    public void setTipoRamo(String tipoRamo) {
        this.tipoRamo = tipoRamo;
    }
    public BigDecimal getPremioAnnuale() {
        return premioAnnuale;
    }
    public void setPremioAnnuale(BigDecimal premioAnnuale) {
        this.premioAnnuale = premioAnnuale;
    }
    public LocalDate getDataScadenza() {
        return dataScadenza;
    }
    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }
    public String getStato() {
        return stato;
    }
    public void setStato(String stato) {
        this.stato = stato;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public List<Sinistro> getSinistri() {
        return sinistri;
    }

    public void setSinistri(List<Sinistro> sinistri) {
        this.sinistri = sinistri;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        if (targa != null) {
            this.targa = targa.toUpperCase().replace(" ", "");
        } else {
            this.targa = null;
        }
    }

    public List<Rinnovo> getRinnovi() {
        return rinnovi;
    }

    public void setRinnovi(List<Rinnovo> rinnovi) {
        this.rinnovi = rinnovi;
    }

    public Boolean isCancellato() {
        return cancellato;
    }

    public void setCancellato(Boolean cancellato) {
        this.cancellato = cancellato;
    }

    public void addRinnovo(Rinnovo rinnovo) {
        if (this.rinnovi == null) {
            this.rinnovi = new java.util.ArrayList<>();
        }
        this.rinnovi.add(rinnovo);
        rinnovo.setPolizza(this); // Imposta il padre nel figlio
    }

    public void removeRinnovo(Rinnovo rinnovo) {
        if (this.rinnovi != null) {
            this.rinnovi.remove(rinnovo);
            rinnovo.setPolizza(null);
        }
    }
}