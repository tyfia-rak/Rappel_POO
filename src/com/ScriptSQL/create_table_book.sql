CREATE TABLE IF NOT EXISTS Books (
    id SERIAL PRIMARY KEY,
    bookName VARCHAR(150) NOT NULL,
    pageNumbers INT NOT NULL,
    topic Topic,
    releaseDate DATE
);

-- Ajouts des trois lignes de données
INSERT INTO Books (bookName, pageNumbers, topic, releaseDate)
VALUES
    ('Book1', 200, 'COMEDY', '2023-01-01'),
    ('Book2', 250, 'ROMANCE', '2023-02-01'),
    ('Book3', 180, 'OTHER', '2023-03-01');