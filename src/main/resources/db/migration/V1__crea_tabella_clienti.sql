CREATE TABLE clienti (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cognome VARCHAR(100) NOT NULL,
    codice_fiscale VARCHAR(16) UNIQUE NOT NULL,
    data_nascita DATE NOT NULL,
    luogo_nascita VARCHAR(100) NOT NULL,
    email VARCHAR(150),
    telefono VARCHAR(20),
    indirizzo_residenza VARCHAR(255),
    cancellato BOOLEAN DEFAULT FALSE
);