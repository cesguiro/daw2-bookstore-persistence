package es.cesguiro.persistence.dao.jpa.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
public class BookJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isbn;
    @Column(name = "title_es")
    private String titleEs;
    @Column(name = "title_en")
    private String titleEn;
    @Column(name = "synopsis_es", length = 2000)
    private String synopsisEs;
    @Column(name = "synopsis_en", length = 2000)
    private String synopsisEn;
    @Column(name = "base_price")
    private BigDecimal basePrice;
    @Column(name = "discount_percentage")
    private BigDecimal discountPercentage;
    private String cover;
    @Column(name = "publication_date")
    private LocalDate publicationDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private PublisherJpaEntity publisher;
    /*@ManyToMany
        @JoinTable(
                name = "books_authors",
                joinColumns = @JoinColumn(name = "book_id"),
                inverseJoinColumns = @JoinColumn(name = "author_id")
        )
        private List<AuthorJpaEntity> authors;*/
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookAuthorJpaEntity> bookAuthors = new ArrayList<>();

    public BookJpaEntity() {
    }

    public BookJpaEntity(Long id, String isbn, String titleEs, String titleEn, String synopsisEs, String synopsisEn, BigDecimal basePrice, BigDecimal discountPercentage, String cover, LocalDate publicationDate, PublisherJpaEntity publisher, List<AuthorJpaEntity> authors) {
        this.id = id;
        this.isbn = isbn;
        this.titleEs = titleEs;
        this.titleEn = titleEn;
        this.synopsisEs = synopsisEs;
        this.synopsisEn = synopsisEn;
        this.basePrice = basePrice;
        this.discountPercentage = discountPercentage;
        this.cover = cover;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
        setAuthors(authors);
    }

    public List<BookAuthorJpaEntity> getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(List<BookAuthorJpaEntity> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }

    public List<AuthorJpaEntity> getAuthors() {
        return bookAuthors.stream().map(BookAuthorJpaEntity::getAuthor).collect(Collectors.toList());
    }

    public void setAuthors(List<AuthorJpaEntity> authors) {
        this.bookAuthors.clear();
        for (AuthorJpaEntity author : authors) {
            BookAuthorJpaEntity bookAuthor = new BookAuthorJpaEntity(this, author);
            this.bookAuthors.add(bookAuthor);
        }
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public PublisherJpaEntity getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherJpaEntity publisher) {
        this.publisher = publisher;
    }

    public String getSynopsisEn() {
        return synopsisEn;
    }

    public void setSynopsisEn(String synopsisEn) {
        this.synopsisEn = synopsisEn;
    }

    public String getSynopsisEs() {
        return synopsisEs;
    }

    public void setSynopsisEs(String synopsisEs) {
        this.synopsisEs = synopsisEs;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleEs() {
        return titleEs;
    }

    public void setTitleEs(String titleEs) {
        this.titleEs = titleEs;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BookJpaEntity other)) {
            return false;
        }
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }


}
