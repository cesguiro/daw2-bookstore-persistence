package es.cesguiro.persistence.repository;

import es.cesguiro.data.loader.AuthorsDataLoader;
import es.cesguiro.data.loader.BooksDataLoader;
import es.cesguiro.data.loader.PublishersDataLoader;
import es.cesguiro.data.mapper.BookMapper;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.persistence.dao.jpa.BookJpaDao;
import es.cesguiro.persistence.dao.jpa.entity.BookJpaEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookRepositoryImplTest {

    @Mock
    private BookJpaDao bookJpaDao;

    @InjectMocks
    private BookRepositoryImpl bookRepositoryImpl;

    private static List<BookEntity> bookEntities;
    private static List<BookJpaEntity> bookJpaEntities;
    private static List<AuthorEntity> authorEntities;
    private static List<PublisherEntity> publisherEntities;

    @BeforeAll
    static void setUp() {
        BooksDataLoader booksDataLoader = new BooksDataLoader();
        bookEntities = booksDataLoader.loadBookEntitiesFromCSV();
        bookJpaEntities = booksDataLoader.loadBookJpaEntitiesFromCSV();

        AuthorsDataLoader authorsDataLoader = new AuthorsDataLoader();
        authorEntities = authorsDataLoader.loadAuthorEntitiesFromCSV();

        PublishersDataLoader publishersDataLoader = new PublishersDataLoader();
        publisherEntities = publishersDataLoader.loadPublisherEntitiesFromCSV();
    }

    @Test
    void testBookMapping() {
        System.out.println("Testing BookEntity to Book mapping...");
    }

}