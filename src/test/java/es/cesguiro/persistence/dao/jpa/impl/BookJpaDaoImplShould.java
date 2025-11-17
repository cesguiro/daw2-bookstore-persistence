package es.cesguiro.persistence.dao.jpa.impl;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import es.cesguiro.persistence.annotation.DaoTest;
import es.cesguiro.persistence.dao.jpa.BookJpaDao;
import es.cesguiro.persistence.dao.jpa.entity.AuthorJpaEntity;
import es.cesguiro.persistence.dao.jpa.entity.BookJpaEntity;
import es.cesguiro.persistence.dao.jpa.entity.PublisherJpaEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@DaoTest
class BookJpaDaoImplShould extends BaseJpaDaoTest<BookJpaDao>{


    @Test
    @DataSet(value= "adapters/data/books.json")
    @ExpectedDataSet(value= "adapters/data/books-after-insert.json", ignoreCols = {"id"})
    void insert_book_correctly() {

        PublisherJpaEntity publisherJpaEntity = new PublisherJpaEntity();
        publisherJpaEntity.setId(1L);

        AuthorJpaEntity authorJpaEntity = new AuthorJpaEntity();
        authorJpaEntity.setId(1L);

        BookJpaEntity newBook = new BookJpaEntity(
                null,
                "9876543210987",
                "Libro 2",
                "Book 2",
                null,
                null,
                new BigDecimal("29.90"),
                new BigDecimal("5.00"),
                null,
                null,
                publisherJpaEntity,
                List.of(authorJpaEntity)
        );

        dao.insert(newBook);
        flushAndCommitForDbRider();
    }

    @Test
    @DataSet(value= "adapters/data/books.json")
    @ExpectedDataSet(value= "adapters/data/books-after-update-simple-fields.json", ignoreCols = {"id"})
    void update_simple_fields_of_book_when_isbn_exists() {
        PublisherJpaEntity publisherJpaEntity = new PublisherJpaEntity();
        publisherJpaEntity.setId(1L);

        AuthorJpaEntity authorJpaEntity = new AuthorJpaEntity();
        authorJpaEntity.setId(1L);


        BookJpaEntity bookToUpdate = new BookJpaEntity(
                1L,
                "1234567890123",
                "Updated Title ES",
                "Updated Title EN",
                "Updated Synopsis ES",
                "Updated Synopsis EN",
                new BigDecimal("99.99"),
                new BigDecimal("99.00"),
                "updated-cover.jpg",
                LocalDate.of(2024, 1, 1),
                publisherJpaEntity,
                List.of(authorJpaEntity)
        );

        dao.update(bookToUpdate);
        flushAndCommitForDbRider();
    }

}