package es.cesguiro.persistence.dao.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "book_author")
public class BookAuthorJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookJpaEntity book;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private AuthorJpaEntity author;

    public BookAuthorJpaEntity() {
    }

    public BookAuthorJpaEntity(BookJpaEntity book, AuthorJpaEntity author) {
        this.book = book;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookJpaEntity getBook() {
        return book;
    }

    public void setBook(BookJpaEntity book) {
        this.book = book;
    }

    public AuthorJpaEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorJpaEntity author) {
        this.author = author;
    }
}
