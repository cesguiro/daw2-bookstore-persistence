CREATE TABLE genres (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        name_es VARCHAR(255) NOT NULL,
                        name_en VARCHAR(255) NOT NULL,
                        slug VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE categories (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            name_es VARCHAR(255) NOT NULL, /* 1 - Novedades, 2 - Recomendados, 3 - Más leídos, 4 - Ofertas */
                            name_en VARCHAR(255) NOT NULL, /* 1 - New Releases, 2 - Recommended, 3 - Best Sellers, 4 - Offers */
                            slug VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE publishers (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(255) NOT NULL,
                            slug VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE books (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       isbn VARCHAR(13) UNIQUE NOT NULL,
                       title_es VARCHAR(255) NOT NULL,
                       title_en VARCHAR(255) NOT NULL,
                       synopsis_es TEXT,
                       synopsis_en TEXT,
                       base_price DECIMAL(10, 2) NOT NULL,
                       discount_percentage DECIMAL(4, 2) DEFAULT 0,
                       cover VARCHAR(255),
                       publication_date DATE,
                       publisher_id INT,
                       category_id INT,
                       FOREIGN KEY (publisher_id) REFERENCES publishers(id),
                       FOREIGN KEY (category_id) REFERENCES categories(id)  -- Índice para mejorar búsquedas por category_id

);

CREATE INDEX idx_books_publisher_id ON books (publisher_id);
CREATE INDEX idx_books_category_id ON books (category_id);


CREATE TABLE books_genres (
                              book_id INT,
                              genre_id INT,
                              PRIMARY KEY (book_id, genre_id),
                              FOREIGN KEY (book_id) REFERENCES books(id),
                              FOREIGN KEY (genre_id) REFERENCES genres(id)

);

CREATE INDEX idx_books_genres_book_id ON books_genres (book_id);
CREATE INDEX idx_books_genres_genre_id ON books_genres (genre_id);

CREATE TABLE authors (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(255) NOT NULL,
                         nationality VARCHAR(255),
                         biography_en TEXT,
                         biography_es TEXT,
                         birth_year INT,
                         death_year INT,
                         slug VARCHAR(255) UNIQUE
);

CREATE TABLE books_authors (
                               book_id INT,
                               author_id INT,
                               PRIMARY KEY (book_id, author_id),
                               FOREIGN KEY (book_id) REFERENCES books(id),
                               FOREIGN KEY (author_id) REFERENCES authors(id)
);

CREATE INDEX idx_books_authors_book_id ON books_authors (book_id);
CREATE INDEX idx_books_authors_author_id ON books_authors (author_id);

CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       address VARCHAR(255) NOT NULL,
                       language VARCHAR(3) NOT NULL DEFAULT 'es',
                       is_admin BOOLEAN NOT NULL DEFAULT 0
);

CREATE TABLE reviews (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         book_id INT,
                         user_id INT,
                         review TEXT,
                         rating INT CHECK (rating >= 1 AND rating <= 5), /* 1 - 5 */
                         FOREIGN KEY (book_id) REFERENCES books(id),
                         FOREIGN KEY (user_id) REFERENCES users(id)

);

CREATE INDEX idx_reviews_book_id ON reviews (book_id);
CREATE INDEX idx_reviews_user_id ON reviews (user_id);

CREATE TABLE orders (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        user_id INT,
                        order_date DATE,
                        delivery_date DATE,
                        status INT NOT NULL DEFAULT 0, -- 0: shopping cart, 1: ordered, 2: in process, 3: sent, 4: received
                        total DECIMAL(10, 2),
                        FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_orders_user_id ON orders (user_id);

CREATE TABLE orders_details (
                                id INT PRIMARY KEY AUTO_INCREMENT,
                                order_id INT,
                                book_id INT,
                                quantity INT NOT NULL,
                                price DECIMAL(10, 2) NOT NULL,
                                FOREIGN KEY (order_id) REFERENCES orders(id),
                                FOREIGN KEY (book_id) REFERENCES books(id)
);

CREATE INDEX idx_orders_details_order_id ON orders_details (order_id);
CREATE INDEX idx_orders_details_book_id ON orders_details (book_id);
