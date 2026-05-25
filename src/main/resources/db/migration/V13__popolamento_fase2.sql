-- ==============================================================================
-- AGGIORNAMENTO DATI ESISTENTI (Inseriti in V7)
-- ==============================================================================
-- Valorizziamo i nuovi campi delle polizze inseriti nella Fase 1
UPDATE polizze SET premio_annuale = 450.00, data_scadenza = '2026-01-10', stato = 'Attiva' WHERE numero_polizza = 'POL-1001';
UPDATE polizze SET premio_annuale = 120.50, data_scadenza = '2026-01-10', stato = 'Attiva' WHERE numero_polizza = 'POL-1002';
UPDATE polizze SET premio_annuale = 315.00, data_scadenza = '2026-03-15', stato = 'Attiva' WHERE numero_polizza = 'POL-1003';
UPDATE polizze SET premio_annuale = 850.00, data_scadenza = '2026-06-01', stato = 'Attiva' WHERE numero_polizza = 'POL-1004';
UPDATE polizze SET premio_annuale = 380.00, data_scadenza = '2026-11-20', stato = 'Attiva' WHERE numero_polizza = 'POL-1005';

-- ==============================================================================
-- INSERIMENTO NUOVI DATI (Sfruttando le nuove logiche e campi di Auditing)
-- ==============================================================================
-- Nuovo Cliente
INSERT INTO clienti (nome, cognome, codice_fiscale, data_nascita, luogo_nascita, email, telefono, indirizzo_residenza, created_by, created_date) 
VALUES 
('Luca', 'Ferrari', 'FRRLCU95A01H501K', '1995-01-01', 'Firenze', 'luca.ferrari@email.it', '3331112223', 'Via Dante 2, Firenze', 'admin', CURRENT_TIMESTAMP);

-- Nuova Polizza legata al cliente appena creato
INSERT INTO polizze (numero_polizza, tipo_ramo, targa, cliente_id, premio_annuale, data_scadenza, stato, created_by, created_date) 
VALUES 
('POL-1006', 'Infortuni', 'QQ123WW', (SELECT id FROM clienti WHERE codice_fiscale = 'FRRLCU95A01H501K'), 250.00, '2027-01-01', 'Attiva', 'admin', CURRENT_TIMESTAMP),
('POL-1007', 'RC Auto', 'ZZ999XX', (SELECT id FROM clienti WHERE codice_fiscale = 'FRRLCU95A01H501K'), 950.00, '2025-05-01', 'Scaduta', 'admin', CURRENT_TIMESTAMP);

-- Nuovo Sinistro legato alla Polizza 1007
INSERT INTO sinistri (numero_sinistro, data_evento, descrizione, stima_danno, polizza_id, stato_lavorazione) 
VALUES 
('SIN-2026-002', '2026-05-15', 'Tamponamento in rotonda. Colpa 50/50.', 850.00, (SELECT id FROM polizze WHERE numero_polizza = 'POL-1007'), 'IN_LAVORAZIONE');
