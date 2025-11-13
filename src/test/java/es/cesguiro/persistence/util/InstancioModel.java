package es.cesguiro.persistence.util;

import es.cesguiro.domain.model.Author;
import es.cesguiro.domain.model.Book;
import es.cesguiro.domain.model.Publisher;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.domain.service.dto.AuthorDto;
import es.cesguiro.domain.service.dto.BookDto;
import es.cesguiro.domain.service.dto.PublisherDto;
import es.cesguiro.persistence.dao.jpa.entity.AuthorJpaEntity;
import es.cesguiro.persistence.dao.jpa.entity.BookAuthorJpaEntity;
import es.cesguiro.persistence.dao.jpa.entity.BookJpaEntity;
import es.cesguiro.persistence.dao.jpa.entity.PublisherJpaEntity;
import org.instancio.Instancio;
import org.instancio.Model;

import java.math.BigDecimal;
import java.util.List;

import static org.instancio.Select.field;

public class InstancioModel {

    private static final String ISBN_PATTERN = "#d#d#d#d#d#d#d#d#d#d#d#d#d";
    private static final String SLUG_PATTERN = "#c#c#c-#c#c#c";

    /*******************************************************************************
     * Modelos de Publisher
     **************************************************************************/
    public static final Model<PublisherEntity> PUBLISHER_ENTITY_MODEL = Instancio.of(PublisherEntity.class)
            .generate(field(PublisherEntity.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
            .toModel();
    public static final Model<PublisherJpaEntity> PUBLISHER_JPA_ENTITY_MODEL = Instancio.of(PublisherJpaEntity.class)
            .generate(field(PublisherJpaEntity.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
            //.ignore(field(PublisherJpaEntity.class, "books"))
            .toModel();
    /*******************************************************************************
     * Modelos de Author
     **************************************************************************/
    public static final Model<AuthorEntity> AUTHOR_ENTITY_MODEL = Instancio.of(AuthorEntity.class)
            .generate(field(AuthorEntity.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
            .toModel();
    public static final Model<AuthorJpaEntity> AUTHOR_JPA_ENTITY_MODEL = Instancio.of(AuthorJpaEntity.class)
            .generate(field(AuthorJpaEntity.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
            //.ignore(field(AuthorJpaEntity.class, "bookAuthors"))
            .toModel();

    public static Model<List<AuthorEntity>> AUTHOR_ENTITY_LIST_MODEL = Instancio.ofList(AUTHOR_ENTITY_MODEL)
            .toModel();
    public static Model<List<AuthorJpaEntity>> AUTHOR_JPA_ENTITY_LIST_MODEL = Instancio.ofList(AUTHOR_JPA_ENTITY_MODEL)
            .toModel();

    /*******************************************************************************
     * Modelos de BookAuthor
     **************************************************************************/
    public static final Model<BookAuthorJpaEntity> BOOK_AUTHOR_JPA_ENTITY_MODEL = Instancio.of(BookAuthorJpaEntity.class)
            .setModel(field(BookAuthorJpaEntity::getAuthor), AUTHOR_JPA_ENTITY_MODEL)
            .toModel();

    public static final Model<List<BookAuthorJpaEntity>> BOOK_AUTHOR_JPA_ENTITY_LIST_MODEL = Instancio.ofList(BOOK_AUTHOR_JPA_ENTITY_MODEL)
            .toModel();

    /*******************************************************************************
     * Modelos de Book
     **************************************************************************/

    public static final Model<BookEntity> BOOK_ENTITY_MODEL = Instancio.of(BookEntity.class)
            .generate(field(BookEntity::isbn), gen -> gen.text().pattern(ISBN_PATTERN))
            .generate(field(BookEntity::basePrice), gen -> gen.math().bigDecimal().range(new BigDecimal("0.10"), new BigDecimal("500.00")))
            .generate(field(BookEntity::discountPercentage), gen -> gen.math().bigDecimal().range(new BigDecimal("0.00"), new BigDecimal("100.00")))
            .generate(field(BookEntity::publicationDate), gen -> gen.temporal().localDate().past())
            .setModel(field(BookEntity::publisher), PUBLISHER_ENTITY_MODEL)
            .setModel(field(BookEntity::authors), AUTHOR_ENTITY_LIST_MODEL)
            .toModel();
    public static final Model<BookJpaEntity> BOOK_JPA_ENTITY_MODEL = Instancio.of(BookJpaEntity.class)
            .generate(field(BookJpaEntity::getIsbn), gen -> gen.text().pattern(ISBN_PATTERN))
            .generate(field(BookJpaEntity::getBasePrice), gen -> gen.math().bigDecimal().range(new BigDecimal("0.10"), new BigDecimal("500.00")))
            .generate(field(BookJpaEntity::getDiscountPercentage), gen -> gen.math().bigDecimal().range(new BigDecimal("0.00"), new BigDecimal("100.00")))
            .generate(field(BookJpaEntity::getPublicationDate), gen -> gen.temporal().localDate().past())
            .setModel(field(BookJpaEntity::getPublisher), PUBLISHER_JPA_ENTITY_MODEL)
            .setModel(field(BookJpaEntity::getBookAuthors), BOOK_AUTHOR_JPA_ENTITY_LIST_MODEL)
            .toModel();

}
