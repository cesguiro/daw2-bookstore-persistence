package es.cesguiro.persistence.dao.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "book_authors")
public class BookAuthorJpaEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookJpaEntity book;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private AuthorJpaEntity author;


}
