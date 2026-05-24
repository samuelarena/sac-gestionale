CREATE TABLE sinistri (
    id Serial PRIMARY KEY,
    numero_sinistro VARCHAR(50) UNIQUE NOT NULL,
    data_sinistro DATE NOT NULL,
    descrizione TEXT,
    danno_stimato NUMERIC(10, 2),
    polizza_id INTEGER NOT NULL,
    cancellato BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_sinistro_polizza FOREIGN KEY (polizza_id) REFERENCES polizze (id)
)