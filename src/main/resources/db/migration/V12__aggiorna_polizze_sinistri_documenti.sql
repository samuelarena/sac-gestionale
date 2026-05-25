-- Aggiorna la tabella polizze
ALTER TABLE polizze RENAME COLUMN tipo TO tipo_ramo;
ALTER TABLE polizze ADD COLUMN premio_annuale NUMERIC(10, 2);
ALTER TABLE polizze ADD COLUMN data_scadenza DATE;
ALTER TABLE polizze ADD COLUMN stato VARCHAR(255) DEFAULT 'Attiva';

-- Aggiorna la tabella sinistri
ALTER TABLE sinistri RENAME COLUMN data_sinistro TO data_evento;
ALTER TABLE sinistri RENAME COLUMN danno_stimato TO stima_danno;
ALTER TABLE sinistri RENAME COLUMN stato_pratica TO stato_lavorazione;

-- Aggiorna la tabella documenti
ALTER TABLE documenti ALTER COLUMN cliente_id DROP NOT NULL;
ALTER TABLE documenti ADD COLUMN sinistro_id INT;
ALTER TABLE documenti ADD CONSTRAINT fk_documento_sinistro FOREIGN KEY (sinistro_id) REFERENCES sinistri(id);
