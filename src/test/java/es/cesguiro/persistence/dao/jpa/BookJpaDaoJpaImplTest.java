package es.cesguiro.persistence.dao.jpa;

import es.cesguiro.data.loader.AuthorsDataLoader;
import es.cesguiro.data.loader.BooksDataLoader;
import es.cesguiro.data.loader.PublishersDataLoader;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.persistence.TestConfig;
import es.cesguiro.persistence.dao.jpa.entity.AuthorJpaEntity;
import es.cesguiro.persistence.dao.jpa.entity.BookJpaEntity;
import es.cesguiro.persistence.dao.jpa.entity.PublisherJpaEntity;
import es.cesguiro.persistence.repository.mapper.AuthorMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookJpaDaoJpaImplTest {

    /*@PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BookJpaDao bookJpaDao;

    @Autowired
    private PublisherJpaDao publisherJpaDao;

    @Autowired
    private AuthorJpaDao authorJpaDao;

    static Stream<Arguments> provideInsertBookData() {
        return Stream.of(
                Arguments.of(publisherEntities.getFirst(), List.of(authorEntities.get(0), authorEntities.get(1))),
                Arguments.of(publisherEntities.get(1), List.of(authorEntities.get(2))),
                Arguments.of(new PublisherEntity(1L, null, null), List.of(authorEntities.get(2))),
                Arguments.of(publisherEntities.get(1), List.of(
                        new AuthorEntity(1L, null, null, null, null, null, null, null)
                ))
        );
    }
    @ParameterizedTest
    @MethodSource("provideInsertBookData")
    @DisplayName("Test insert method persists BookJpaEntity")
    void testInsert(PublisherJpaEntity publisherJpaEntity, List<AuthorJpaEntity> authorJpaEntities) {
        BookJpaEntity newBook = new BookJpaEntity(
                null,
                "666666666666",
                "New Book Title ES",
                "New Book Title EN",
                "New Book Synopsis ES",
                "New Book Synopsis EN",
                BigDecimal.valueOf(29.99),
                10.0,
                "new_book_cover.jpg",
                LocalDate.of(2024, 1, 1).toString(),
                publisherJpaEntity, // Assuming the first publisher exists
                authorJpaEntities // Assuming the first two authors exist
        );

        String sql = "SELECT COUNT(b) FROM BookJpaEntity b";
        long countBefore = entityManager.createQuery(sql, Long.class)
                .getSingleResult();

        BookJpaEntity result = bookJpaDao.insert(newBook);

        long countAfter = entityManager.createQuery(sql, Long.class)
                .getSingleResult();

        long lastId = entityManager.createQuery("SELECT MAX(b.id) FROM BookJpaEntity b", Long.class)
                .getSingleResult();

        Set<Long> expectedAuthorIds = newBook.getAuthors().stream()
                .map(AuthorJpaEntity::getId)
                .collect(Collectors.toSet());
        Set<Long> resultAuthorIds = result.getAuthors().stream()
                .map(AuthorJpaEntity::getId)
                .collect(Collectors.toSet());

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(lastId, result.getId()),
                () -> assertEquals(newBook.getIsbn(), result.getIsbn()),
                () -> assertEquals(newBook.getTitleEs(), result.getTitleEs()),
                () -> assertEquals(newBook.getTitleEn(), result.getTitleEn()),
                () -> assertEquals(newBook.getSynopsisEs(), result.getSynopsisEs()),
                () -> assertEquals(newBook.getSynopsisEn(), result.getSynopsisEn()),
                () -> assertEquals(newBook.getBasePrice(), result.getBasePrice()),
                () -> assertEquals(newBook.getDiscountPercentage(), result.getDiscountPercentage()),
                () -> assertEquals(newBook.getCover(), result.getCover()),
                () -> assertEquals(newBook.getPublicationDate(), result.getPublicationDate()),
                () -> assertEquals(newBook.getPublisher().getId(), result.getPublisher().getId()),
                () -> assertEquals(newBook.getAuthors().size(), result.getAuthors().size()),
                () -> assertEquals(expectedAuthorIds, resultAuthorIds),
                () -> assertEquals(countBefore + 1, countAfter)
        );
    }

    @ParameterizedTest
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