package com.sac_gestionale.dto;
import java.time.LocalDate;

public class ClienteDettaglioDTO {
    private Integer id;

    private String nome;
    private String cognome;
    private String codiceFiscale;
    private LocalDate dataNascita;
    private String luogoNascita;
    private String email;
    private String telefono;
    private String indirizzoResidenza;
    
    public ClienteDettaglioDTO(Integer id, String nome, String cognome, String codiceFiscale, LocalDate dataNascita, String luogoNascita, String email, String telefono, String indirizzoResidenza) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.dataNascita = dataNascita;
        this.luogoNascita = luogoNascita;
        this.email = email;
        this.telefono = telefono;
        this.indirizzoResidenza = indirizzoResidenza;
    }
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

    
}
