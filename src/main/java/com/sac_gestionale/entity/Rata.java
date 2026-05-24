package com.sac_gestionale.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "rate")
public class Rata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relazione: Questa rata appartiene a un Rinnovo specifico
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rinnovo_id", nullable = false)
    @JsonIgnore // Evita loop infiniti quando restituiamo i dati a Swagger
    private Rinnovo rinnovo;

    @Column(nullable = false)
    private BigDecimal importo;

    @Column(name = "data_scadenza", nullable = false)
    private LocalDate dataScadenza;

    @Column(nullable = false)
    private boolean pagata = false; // Di default la rata non è pagata

    // Getter e Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Rinnovo getRinnovo() { return rinnovo; }
    public void setRinnovo(Rinnovo rinnovo) { this.rinnovo = rinnovo; }

    public BigDecimal getImporto() { return importo; }
    public void setImporto(BigDecimal importo) { this.importo = importo; }

    public LocalDate getDataScadenza() { return dataScadenza; }
    public void setDataScadenza(LocalDate dataScadenza) { this.dataScadenza = dataScadenza; }

    public boolean isPagata() { return pagata; }
    public void setPagata(boolean pagata) { this.pagata = pagata; }
}