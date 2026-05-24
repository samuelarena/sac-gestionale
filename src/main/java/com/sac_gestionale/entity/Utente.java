package com.sac_gestionale.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "utenti")
public class Utente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank
    private String username;
    
    @NotBlank
    private String password; // Aggiunto "private"
    
    private Boolean active; // Aggiunto "private"
    
    @ManyToOne
    @JoinColumn(name = "ruolo_id")
    @JsonBackReference
    private Ruolo ruolo;
    
    // Costruttore vuoto obbligatorio per Hibernate/JPA
    public Utente() {
    }
    
    public Utente(Integer id, @NotBlank String username, @NotBlank String password, Boolean active, Ruolo ruolo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.active = active;
        this.ruolo = ruolo;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public Ruolo getRuolo() {
        return ruolo;
    }
    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }
}