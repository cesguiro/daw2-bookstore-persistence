package es.cesguiro.persistence.dao.jpa.impl;

import es.cesguiro.persistence.TestConfig;
import es.cesguiro.persistence.dao.jpa.AuthorJpaDao;
import es.cesguiro.persistence.dao.jpa.BookJpaDao;
import es.cesguiro.persistence.dao.jpa.PublisherJpaDao;
import es.cesguiro.persistence.dao.jpa.entity.AuthorJpaEntity;
import es.cesguiro.persistence.dao.jpa.entity.BookJpaEntity;
import es.cesguiro.persistence.dao.jpa.entity.PublisherJpaEntity;
import es.cesguiro.persistence.util.InstancioModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookJpaDaoImplShould {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BookJpaDao bookJpaDao;

    @Autowired
    private PublisherJpaDao publisherJpaDao;

    @Autowired
    private AuthorJpaDao authorJpaDao;


    @Test
    @DisplayName("Test insert method persists BookJpaEntity")
    void insert_book_when_data_is_correct() {
        PublisherJpaEntity publisherJpaEntity = Instancio.of(InstancioModel.PUBLISHER_JPA_ENTITY_MODEL)
                .ignore(field(PublisherJpaEntity::getId))
                .create();
        entityManager.persist(publisherJpaEntity);

        List<AuthorJpaEntity> authorJpaEntities =Instancio.ofList(InstancioModel.AUTHOR_JPA_ENTITY_MODEL)
                .size(2)
                .ignore(field(AuthorJpaEntity::getId))
                .create();
        authorJpaEntities.forEach(entityManager::persist);

        BookJpaEntity newBook = Instancio.of(InstancioModel.BOOK_JPA_ENTITY_MODEL)
                .ignore(field(BookJpaEntity::getId))
                .set(field(BookJpaEntity::getPublisher), publisherJpaEntity)
                .lenient()
                .create();

        newBook.setAuthors(authorJpaEntities);

        long countBefore = bookJpaDao.count();
        BookJpaEntity result = bookJpaDao.insert(newBook);
        long countAfter = bookJpaDao.count();

        assertThat(result)
                .isNotNull()
                .extracting(BookJpaEntity::getId)
                .isNotNull();
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(newBook);
        assertThat(result.getAuthors())
                .extracting(AuthorJpaEntity::getId)
                .containsExactlyInAnyOrderElementsOf(
                        authorJpaEntities.stream()
                                .map(AuthorJpaEntity::getId)
                                .collect(Collectors.toSet())
                );
        assertThat(countAfter).isEqualTo(countBefore + 1);
    }

    @Test
    void update_simple_fields_of_book_when_isbn_exists() {
        BookJpaEntity bookToUpdate = bookJpaDao.findById(1L).orElseThrow(
                () -> new IllegalStateException("Book with id 1L not found")
        );

        bookToUpdate.setTitleEs("Updated Title ES " + now());
        bookToUpdate.setTitleEn("Updated Title EN " + now());
        bookToUpdate.setSynopsisEs("Updated Synopsis ES " + now());
        bookToUpdate.setSynopsisEn("Updated Synopsis EN " + now());
        bookToUpdate.setBasePrice(new BigDecimal("99.99"));
        bookToUpdate.setDiscountPercentage(new BigDecimal("99.00"));
        bookToUpdate.setPublicationDate(LocalDate.now());
        bookToUpdate.setCover("updated-cover-" + now() + ".jpg");

        BookJpaEntity result = bookJpaDao.update(bookToUpdate);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(bookToUpdate);

    }
}