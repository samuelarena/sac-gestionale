CREATE TABLE rate (
    id BIGSERIAL PRIMARY KEY, -- Corrisponde al Long id
    rinnovo_id BIGINT NOT NULL, -- La chiave esterna che punta a Rinnovo
    importo NUMERIC(10, 2) NOT NULL, -- Corrisponde a BigDecimal
    data_scadenza DATE NOT NULL, -- Corrisponde a LocalDate
    pagata BOOLEAN NOT NULL DEFAULT FALSE, -- Corrisponde a boolean
    
    -- Creiamo il collegamento (Foreign Key) con la tabella rinnovi
    CONSTRAINT fk_rinnovo_rata FOREIGN KEY (rinnovo_id) REFERENCES rinnovi (id)
);