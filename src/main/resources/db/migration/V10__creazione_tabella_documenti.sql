CREATE TABLE documenti (
    id BIGSERIAL PRIMARY KEY,
    nome_file VARCHAR(255) NOT NULL,
    tipo VARCHAR(100),
    percorso_fisico VARCHAR(500) NOT NULL,
    data_caricamento DATE NOT NULL,
    cliente_id INTEGER NOT NULL,
    CONSTRAINT fk_documenti_clienti FOREIGN KEY (cliente_id) REFERENCES clienti (id)
);
