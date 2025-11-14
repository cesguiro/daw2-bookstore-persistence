package es.cesguiro.persistence.dao.jpa.impl;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import es.cesguiro.persistence.TestConfig;
import es.cesguiro.persistence.annotation.DaoTest;
import es.cesguiro.persistence.dao.jpa.AuthorJpaDao;
import es.cesguiro.persistence.dao.jpa.BookJpaDao;
import es.cesguiro.persistence.dao.jpa.PublisherJpaDao;
import es.cesguiro.persistence.dao.jpa.entity.AuthorJpaEntity;
import es.cesguiro.persistence.dao.jpa.entity.BookJpaEntity;
import es.cesguiro.persistence.dao.jpa.entity.PublisherJpaEntity;
import es.cesguiro.persistence.util.InstancioModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.time.LocalTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertThrows;

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