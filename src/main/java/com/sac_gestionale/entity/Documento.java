package com.sac_gestionale.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "documenti")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeFile;
    private String tipo;
    private String percorsoFisico;
    private LocalDate dataCaricamento;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonBackReference
    private Cliente cliente;

    public Documento() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPercorsoFisico() {
        return percorsoFisico;
    }

    public void setPercorsoFisico(String percorsoFisico) {
        this.percorsoFisico = percorsoFisico;
    }

    public LocalDate getDataCaricamento() {
        return dataCaricamento;
    }

    public void setDataCaricamento(LocalDate dataCaricamento) {
        this.dataCaricamento = dataCaricamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
