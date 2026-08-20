-- ============================================================
-- Script DDL - Examen_Final_POO
-- Gestion des mouvements de stock
-- Base : PostgreSQL
-- ============================================================

-- ============================================================
-- Table PRODUCT
-- ============================================================
CREATE TABLE product (
    id           VARCHAR(36)     NOT NULL,
    name         VARCHAR(150)    NOT NULL,
    description  TEXT            NOT NULL,
    unit_price   NUMERIC(10,2)   NOT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id),
    CONSTRAINT ck_product_unit_price_positive CHECK (unit_price >= 0)
);

-- ============================================================
-- Table STOCK_MOVEMENT
-- ============================================================
CREATE TABLE stock_movement (
    id             VARCHAR(36)     NOT NULL,
    created_at     TIMESTAMPTZ     NOT NULL DEFAULT now(),
    movement_type  VARCHAR(3)      NOT NULL,
    quantity       INTEGER         NOT NULL,
    product_id     VARCHAR(36)     NOT NULL,
    CONSTRAINT pk_stock_movement PRIMARY KEY (id),
    CONSTRAINT ck_stock_movement_type CHECK (movement_type IN ('IN', 'OUT')),
    CONSTRAINT ck_stock_movement_quantity_positive CHECK (quantity > 0),
    CONSTRAINT fk_stock_movement_product FOREIGN KEY (product_id)
        REFERENCES product (id)
        ON DELETE RESTRICT
);

-- ============================================================
-- Index (justifiés par les endpoints réels de l'examen)
-- ============================================================

-- Justifié par : GET /products/{id}/stock-movements
--            et : GET /products/{id}/stock
-- (filtrage systématique par product_id)
CREATE INDEX idx_stock_movement_product_id ON stock_movement (product_id);

-- Justifié par : GET /stock-movements?type=in|out
-- (filtrage systématique par movement_type)
CREATE INDEX idx_stock_movement_type ON stock_movement (movement_type);

-- ============================================================
-- Données de seed
-- Nécessaires car aucun endpoint POST /products n'existe
-- dans le périmètre strict de l'examen (endpoints fournis).
-- ============================================================
INSERT INTO product (id, name, description, unit_price) VALUES
    ('11111111-1111-1111-1111-111111111111', 'Clavier mécanique', 'Clavier mécanique switch bleu', 49.99),
    ('22222222-2222-2222-2222-222222222222', 'Souris sans fil', 'Souris optique sans fil 2.4GHz', 19.90);
