package es.cesguiro.persistence.dao.jpa;

import es.cesguiro.data.loader.AuthorsDataLoader;
import es.cesguiro.data.loader.BooksDataLoader;
import es.cesguiro.data.loader.PublishersDataLoader;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.persistence.TestConfig;
import es.cesguiro.persistence.dao.BookDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookDaoJpaTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BookDao bookDao;

    private static List<BookEntity> bookEntities;
    private static List<PublisherEntity> publisherEntities;
    private static List<AuthorEntity> authorEntities;

    @BeforeAll
    static void setUp() {
        BooksDataLoader booksDataLoader = new BooksDataLoader();
        PublishersDataLoader publishersDataLoader = new PublishersDataLoader();
        AuthorsDataLoader authorsDataLoader = new AuthorsDataLoader();

        bookEntities = booksDataLoader.loadBookEntitiesFromCSV();
        publisherEntities = publishersDataLoader.loadPublisherEntitiesFromCSV();
        authorEntities = authorsDataLoader.loadAuthorEntitiesFromCSV();

    }

    @Test
    @DisplayName("Test insert method persists BookEntity")
    void testInsert() {
        BookEntity newBook = new BookEntity(
                null,
                "666666666666",
                "New Book Title ES",
                "New Book Title EN",
                "New Book Synopsis ES",
                "New Book Synopsis EN",
                BigDecimal.valueOf(29.99),
                10.0,
                "new_book_cover.jpg",
                LocalDate.of(2024, 1, 1),
                publisherEntities.getFirst(), // Assuming the first publisher exists
                List.of(authorEntities.get(0), authorEntities.get(1)) // Assuming the first two authors exist
        );

        String sql = "SELECT COUNT(b) FROM BookJpaEntity b";
        long countBefore = entityManager.createQuery(sql, Long.class)
                .getSingleResult();

        BookEntity result = bookDao.insert(newBook);

        long countAfter = entityManager.createQuery(sql, Long.class)
                .getSingleResult();

        long lastId = entityManager.createQuery("SELECT MAX(b.id) FROM BookJpaEntity b", Long.class)
                .getSingleResult();

        Set<Long> expectedAuthorIds = newBook.authors().stream()
                .map(AuthorEntity::id)
                .collect(Collectors.toSet());
        Set<Long> resultAuthorIds = result.authors().stream()
                .map(AuthorEntity::id)
                .collect(Collectors.toSet());

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(lastId, result.id()),
                () -> assertEquals(newBook.isbn(), result.isbn()),
                () -> assertEquals(newBook.titleEs(), result.titleEs()),
                () -> assertEquals(newBook.titleEn(), result.titleEn()),
                () -> assertEquals(newBook.synopsisEs(), result.synopsisEs()),
                () -> assertEquals(newBook.synopsisEn(), result.synopsisEn()),
                () -> assertEquals(newBook.basePrice(), result.basePrice()),
                () -> assertEquals(newBook.discountPercentage(), result.discountPercentage()),
                () -> assertEquals(newBook.cover(), result.cover()),
                () -> assertEquals(newBook.publicationDate(), result.publicationDate()),
                () -> assertEquals(newBook.publisher().id(), result.publisher().id()),
                () -> assertEquals(newBook.authors().size(), result.authors().size()),
                () -> assertEquals(expectedAuthorIds, resultAuthorIds),
                () -> assertEquals(countBefore + 1, countAfter)
        );
    }

    @ParameterizedTest
    @DisplayName("Test update method modifies existing BookEntity")
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
        BookEntity bookToUpdate = bookEntities.getFirst();

        // Construimos la lista de AuthorEntity correspondientes
        List<AuthorEntity> updatedAuthors = authorEntities.stream()
                .filter(a -> authorIds.contains(a.id()))
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
        BookEntity result = bookDao.update(updatedBook);

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
    }
}