package com.sac_gestionale.entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity // Dichiara che questa classe è un'Entità collegata al database
@Table(name = "clienti") // Specifica il nome esatto della tabella su PostgreSQL
@SQLDelete(sql = "UPDATE clienti SET cancellato = true WHERE id = ?")
@SQLRestriction("cancellato = false")
public class Cliente extends Auditable {

    @Id // Dichiara che questo campo è la Chiave Primaria (PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Dice al DB di generare l'ID in automatico (1, 2, 3...)
    private Integer id;

    private String nome;
    private String cognome;
    private String codiceFiscale;
    private LocalDate dataNascita;
    private String luogoNascita;
    private String email;
    private String telefono;
    private String indirizzoResidenza;
    @OneToMany (mappedBy = "cliente")
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties("cliente")
    private List<Polizza> polizze;
    @OneToMany(mappedBy = "cliente", cascade = jakarta.persistence.CascadeType.ALL)
    @JsonManagedReference
    private List<Documento> documenti;
    @Column(columnDefinition = "boolean default false")
    private Boolean cancellato = false;

    public Cliente() {
    }

    // ==========================================
    // SEZIONE GETTER E SETTER
    // ==========================================

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getLuogoNascita() {
        return luogoNascita;
    }

    public void setLuogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIndirizzoResidenza() {
        return indirizzoResidenza;
    }

    public void setIndirizzoResidenza(String indirizzoResidenza) {
        this.indirizzoResidenza = indirizzoResidenza;
    }

    public List<Polizza> getPolizze() {
        return polizze;
    }

    public void setPolizze(List<Polizza> polizze) {
        this.polizze = polizze;
    }

    public Boolean isCancellato() {
        return cancellato;
    }

    public void setCancellato(Boolean cancellato) {
        this.cancellato = cancellato;
    }

    public List<Documento> getDocumenti() {
        return documenti;
    }

    public void setDocumenti(List<Documento> documenti) {
        this.documenti = documenti;
    }
    
}