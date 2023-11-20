CREATE TABLE IF NOT EXISTS Visitors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    role VARCHAR(50) NOT NULL,
    reference VARCHAR(08) NOT NULL
);

-- Ajouts de quelques visiteurs
INSERT INTO Visitors (name, role, reference)
VALUES
    ('Sarobidy', 'Guest', 'STD22060'),
    ('Lahatra', 'Guest', 'STD22016'),
    ('Anthonio', 'Guest', 'STD20000');