-- 1. Creazione della nuova tabella figlia 'rinnovi'
CREATE TABLE rinnovi (
    id BIGSERIAL PRIMARY KEY,
    polizza_id INTEGER NOT NULL,
    premio NUMERIC(10, 2) NOT NULL,
    data_stipula DATE NOT NULL,
    data_scadenza DATE NOT NULL,
    attivo BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_rinnovo_polizza FOREIGN KEY (polizza_id) REFERENCES polizze(id) ON DELETE CASCADE
);

INSERT INTO rinnovi (polizza_id, premio, data_stipula, data_scadenza, attivo)
SELECT id, premio, data_stipula, data_scadenza, TRUE
FROM polizze;

ALTER TABLE polizze 
    DROP COLUMN premio,
    DROP COLUMN data_stipula,
    DROP COLUMN data_scadenza;