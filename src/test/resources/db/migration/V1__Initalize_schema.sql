-- ===========================
-- TABLAS PRINCIPALES
-- ===========================

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
                       publisher_id INT NOT NULL,
                       FOREIGN KEY (publisher_id) REFERENCES publishers(id),
                       INDEX idx_books_publisher (publisher_id)
);

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

CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       address VARCHAR(255) NOT NULL,
                       language VARCHAR(3) NOT NULL DEFAULT 'es',
                       is_admin BOOLEAN NOT NULL DEFAULT 0
);

-- ===========================
-- TABLAS DE UNIÓN / RELACIONES
-- ===========================

CREATE TABLE book_author (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             book_id INT NOT NULL,
                             author_id INT NOT NULL,
                             FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
                             FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE,
                             UNIQUE KEY uq_book_author (book_id, author_id),
                             INDEX idx_book_author_book (book_id),
                             INDEX idx_book_author_author (author_id)
);

-- ===========================
-- TABLAS DE REVIEWS Y PEDIDOS
-- ===========================

CREATE TABLE orders (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        user_id INT NOT NULL,
                        order_date DATETIME,
                        delivery_date DATETIME,
                        status INT NOT NULL DEFAULT 0, -- 0: shopping cart, 1: ordered, 2: in process, 3: sent, 4: received
                        total DECIMAL(10, 2),
                        FOREIGN KEY (user_id) REFERENCES users(id),
                        INDEX idx_orders_user (user_id)
);

CREATE TABLE order_details (
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               order_id INT NOT NULL,
                               book_id INT NOT NULL,
                               quantity INT NOT NULL,
                               price DECIMAL(10, 2) NOT NULL,
                               FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                               FOREIGN KEY (book_id) REFERENCES books(id),
                               UNIQUE KEY uq_order_book (order_id, book_id),
                               INDEX idx_order_details_order (order_id),
                               INDEX idx_order_details_book (book_id)
);
