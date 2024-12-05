CREATE TABLE IF NOT EXISTS movie (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    language VARCHAR(50) NOT NULL,
    duration INT NOT NULL,
    genre VARCHAR(100) NOT NULL,
    poster_url VARCHAR(500),
    trailer_url VARCHAR(500),
    date TIMESTAMP NOT NULL
);
