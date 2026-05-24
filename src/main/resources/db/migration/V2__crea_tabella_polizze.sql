CREATE TABLE polizze(
    id Serial PRIMARY KEY,
    numero_polizza VARCHAR(50) UNIQUE NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    targa VARCHAR(7) NOT NULL,
    data_stipula DATE NOT NULL,
    data_scadenza DATE,
    premio NUMERIC(10, 2),
    cliente_id INTEGER NOT NULL,
    cancellato BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_polizza_cliente FOREIGN KEY (cliente_id) REFERENCES clienti (id)
)