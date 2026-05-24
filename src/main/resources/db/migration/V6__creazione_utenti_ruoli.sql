-- Creazione tabella ruoli
CREATE TABLE ruoli (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- Creazione tabella utenti
CREATE TABLE utenti (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    ruolo_id INTEGER NOT NULL,
    CONSTRAINT fk_ruolo FOREIGN KEY (ruolo_id) REFERENCES ruoli(id)
);

-- Popolamento iniziale: Inserimento dei ruoli fondamentali
INSERT INTO ruoli (name) VALUES ('ROLE_ADMIN');
INSERT INTO ruoli (name) VALUES ('ROLE_OPERATORE');

-- Popolamento iniziale: Inserimento dei primi utenti di sistema
-- id 1 = ROLE_ADMIN | id 2 = ROLE_OPERATORE
INSERT INTO utenti (username, password, active, ruolo_id)
VALUES ('admin', '{noop}admin', true, 1);

INSERT INTO utenti (username, password, active, ruolo_id)
VALUES ('mario', '{noop}mario', true, 2);