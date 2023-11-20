CREATE TABLE IF NOT EXISTS Authors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    sex BOOLEAN NOT NULL
);

-- Ajouts des trois lignes de données
INSERT INTO Authors (name, sex)
VALUES
    ('Tony Robbins', true),
    ('Brené Brown', false),
    ('Stephen R. Covey', true);