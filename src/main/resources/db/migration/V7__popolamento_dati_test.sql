-- ==============================================================================
-- POPOLAMENTO TABELLA: clienti
-- ==============================================================================
INSERT INTO clienti (nome, cognome, codice_fiscale, data_nascita, luogo_nascita, email, telefono, indirizzo_residenza) 
VALUES 
('Mario', 'Rossi', 'RSSMRA80A01H501U', '1980-01-01', 'Roma', 'mario.rossi@email.it', '3331234567', 'Via Roma 10, Roma'),
('Luigi', 'Verdi', 'VRDLGU90B12F205Z', '1990-02-12', 'Milano', 'luigi.verdi@email.it', '3339876543', 'Via Milano 25, Milano'),
('Giulia', 'Bianchi', 'BNCGLI85C45D969Q', '1985-03-15', 'Napoli', 'giulia.bianchi@email.it', '3384567890', 'Via Toledo 40, Napoli'),
('Francesca', 'Neri', 'NRIFNC75E54A662O', '1975-05-14', 'Torino', 'francy.neri@email.it', '3311122334', 'Corso Francia 5, Torino');

-- ==============================================================================
-- POPOLAMENTO TABELLA: polizze
-- (Nota: cliente_id 1=Mario, 2=Luigi, 3=Giulia, 4=Francesca)
-- ==============================================================================
INSERT INTO polizze (numero_polizza, tipo, targa, cliente_id) 
VALUES 
('POL-1001', 'RC Auto', 'AB123CD', 1),
('POL-1002', 'Furto e Incendio', 'AB123CD', 1), -- Seconda polizza sulla stessa auto di Mario
('POL-1003', 'RC Auto', 'EF456GH', 2),
('POL-1004', 'Kasko Completa', 'LM789NO', 3),
('POL-1005', 'RC Auto', 'ZA111ZZ', 4);

-- ==============================================================================
-- POPOLAMENTO TABELLA: rinnovi
-- (Nota: polizza_id segue l'ordine di inserimento qui sopra, da 1 a 5)
-- ==============================================================================
INSERT INTO rinnovi (polizza_id, premio, data_stipula, data_scadenza, attivo) 
VALUES 
-- Polizza 1 (Mario): Rinnovo attivo
(1, 450.00, '2025-01-10', '2026-01-10', true),
-- Polizza 2 (Mario): Rinnovo attivo
(2, 120.50, '2025-01-10', '2026-01-10', true),
-- Polizza 3 (Luigi): Uno storico scaduto, e uno nuovo attivo
(3, 300.00, '2024-03-15', '2025-03-15', false), 
(3, 315.00, '2025-03-15', '2026-03-15', true),
-- Polizza 4 (Giulia): Rinnovo attivo, ma in scadenza a breve!
(4, 850.00, '2025-06-01', '2026-06-01', true),
-- Polizza 5 (Francesca): Rinnovo attivo
(5, 380.00, '2025-11-20', '2026-11-20', true);

-- ==============================================================================
-- POPOLAMENTO TABELLA: sinistri
-- ==============================================================================
INSERT INTO sinistri (numero_sinistro, data_sinistro, descrizione, danno_stimato, polizza_id, stato_pratica) 
VALUES 
-- Sinistro recente, ancora aperto per la polizza 1 (Mario)
('SIN-2025-001', '2025-04-10', 'Tamponamento a catena al semaforo. Danni al paraurti posteriore.', 1250.00, 1, 'APERTO'),
-- Sinistro storico, già chiuso per la polizza 3 (Luigi)
('SIN-2024-089', '2024-08-05', 'Graffio sulla fiancata destra trovato in parcheggio.', 350.00, 3, 'CHIUSO'),
-- Sinistro gravissimo per la polizza 4 (Giulia)
('SIN-2026-012', '2026-01-20', 'Sbandamento per ghiaccio, auto finita nel fosso. Nessun ferito ma danni ingenti.', 4500.00, 4, 'IN_LAVORAZIONE');