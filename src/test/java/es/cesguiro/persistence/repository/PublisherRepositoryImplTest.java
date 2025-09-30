package es.cesguiro.persistence.repository;

import es.cesguiro.data.loader.PublishersDataLoader;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.persistence.dao.PublisherDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublisherRepositoryImplTest {

    @Mock
    private PublisherDao publisherDao;

    @InjectMocks
    private PublisherRepositoryImpl publisherRepositoryImpl;

    private static List<PublisherEntity> publisherEntities;

    @BeforeAll
    static void setUp() {
        PublishersDataLoader publishersDataLoader = new PublishersDataLoader();
        publisherEntities = publishersDataLoader.loadPublisherEntitiesFromCSV();
    }

    @Test
    @DisplayName("Test findBySlug method returns Optional<PublisherEntity>")
    void testFindBySlug() {
        String slug = "editorial-sudamericana";
        PublisherEntity expected = publisherEntities.getFirst();
        when(publisherDao.findBySlug(slug)).thenReturn(Optional.of(expected));

        Optional<PublisherEntity> result = publisherRepositoryImpl.findBySlug(slug);
        assertAll(
                () -> assertTrue(result.isPresent()),
                () -> assertEquals(expected.name(), result.get().name()),
                () -> assertEquals(expected.slug(), result.get().slug())
        );
    }

    @Test
    @DisplayName("Test findBySlug method returns Optional.empty()")
    void testFindBySlugEmpty() {
        String slug = "non-existent-slug";
        when(publisherDao.findBySlug(slug)).thenReturn(Optional.empty());
        assertTrue(publisherRepositoryImpl.findBySlug(slug).isEmpty());
    }

}