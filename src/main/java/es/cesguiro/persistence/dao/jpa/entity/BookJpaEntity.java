package es.cesguiro.persistence.dao.jpa.entity;

import jakarta.persistence.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@RedisHash("Book")
public class BookJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Indexed
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
    private Double basePrice;
    @Column(name = "discount_percentage")
    private Double discountPercentage;
    private String cover;
    @Column(name = "publication_date")
    private String publicationDate;
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
    @OneToMany(mappedBy = "book")
    private List<BookAuthorJpaEntity> bookAuthors = new ArrayList<>();

    public BookJpaEntity() {
    }


    public List<AuthorJpaEntity> getAuthors() {
        return bookAuthors.stream().map(BookAuthorJpaEntity::getAuthor).toList();
    }

    public void setAuthors(List<AuthorJpaEntity> authors) {
        this.bookAuthors.clear();
        for (AuthorJpaEntity author : authors) {
            this.bookAuthors.add(new BookAuthorJpaEntity(this, author));
        }
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
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

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
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
}
