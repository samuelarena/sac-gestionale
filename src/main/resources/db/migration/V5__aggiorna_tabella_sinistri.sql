ALTER TABLE sinistri
ADD COLUMN stato_pratica VARCHAR(50) DEFAULT 'APERTO';

ALTER TABLE sinistri
DROP CONSTRAINT fk_sinistro_polizza;

ALTER TABLE sinistri
ADD CONSTRAINT fk_sinistro_polizza
FOREIGN KEY (polizza_id) REFERENCES polizze (id) ON DELETE CASCADE;