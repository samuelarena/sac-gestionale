package com.sac_gestionale.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ruoli")
public class Ruolo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String name;

    // Costruttore vuoto obbligatorio per Hibernate/JPA
    public Ruolo() {
    }

    public Ruolo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}