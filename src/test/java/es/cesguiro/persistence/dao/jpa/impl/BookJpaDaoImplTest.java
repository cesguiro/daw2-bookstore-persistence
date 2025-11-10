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
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookJpaDaoImplTest {

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
    void testInsert() {
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

    /*@ParameterizedTest
    @DisplayName("Test update method modifies existing BookJpaEntity")
    @Transactional
    @CsvSource({
        "9777777777777, El principito, 15.99, 1, 1",
        "9780142424179, Nuevo título, 15.99, 1, 1",
        "9780142424179, El principito, 19.99, 1, 1",
        "9780142424179, El principito, 15.99, 2, 1",
        "9780142424179, El principito, 15.99, 1, 1;3",
        "9780142424179, El principito, 15.99, 2, 3"
    })
    void testUpdate(String newIsbn,
                    String newTitleEs,
                    double newBasePrice,
                    long newPublisherId,
                    String authorIdsCsv) {
        // Parseamos los IDs de autores separados por ";"
        List<Long> authorIds = Arrays.stream(authorIdsCsv.split(";"))
                .filter(s -> !s.isBlank())
                .map(Long::parseLong)
                .toList();

        // Seleccionamos un libro existente
        BookJpaEntity bookToUpdate = bookJpaDao.findById(bookEntities.getFirst().id())
                .orElseThrow(() -> new IllegalStateException("No book found to update"));

        // Construimos la lista de AuthorJpaEntity correspondientes
        List<AuthorJpaEntity> updatedAuthors = authorEntities.stream()
                .filter(a -> authorIds.contains(a.id()))
                .map(AuthorMapper.INSTANCE::authorEntityToAuthorJpaEntity)
                .toList();

        // Creamos la entidad modificada
        BookEntity updatedBook = new BookEntity(
                bookToUpdate.id(),
                newIsbn,
                newTitleEs,
                bookToUpdate.titleEn(),
                bookToUpdate.synopsisEs(),
                bookToUpdate.synopsisEn(),
                BigDecimal.valueOf(newBasePrice),
                bookToUpdate.discountPercentage(),
                bookToUpdate.cover(),
                bookToUpdate.publicationDate(),
                publisherEntities.stream()
                        .filter(p -> p.id() == newPublisherId)
                        .findFirst()
                        .orElseThrow(),
                updatedAuthors
        );

        // Ejecutamos la actualización
        BookEntity result = bookJpaDao.update(updatedBook);

        // Asserts
        assertAll(
                () -> assertEquals(updatedBook.id(), result.id()),
                () -> assertEquals(newIsbn, result.isbn()),
                () -> assertEquals(newTitleEs, result.titleEs()),
                () -> assertEquals(BigDecimal.valueOf(newBasePrice), result.basePrice()),
                () -> assertEquals(newPublisherId, result.publisher().id()),
                () -> assertEquals(updatedAuthors.size(), result.authors().size()),
                () -> assertTrue(updatedAuthors.stream()
                        .allMatch(author -> result.authors().stream()
                                .anyMatch(a -> a.id().equals(author.id()))))
        );
    }*/
}