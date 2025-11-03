package es.cesguiro.persistence.repository;


import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.persistence.dao.jpa.PublisherJpaDao;
import es.cesguiro.persistence.dao.jpa.entity.PublisherJpaEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublisherRepositoryImplTest {

    @Mock
    private PublisherJpaDao publisherDao;

    @InjectMocks
    private PublisherRepositoryImpl publisherRepositoryImpl;

    @Test
    @DisplayName("Test findBySlug method returns Optional<PublisherEntity>")
    void testFindBySlug() {
        String slug = "editorial-sudamericana";
        PublisherEntity expected = Instancio.of()
                .set(field -> field.slug(), slug)
                .create();

        PublisherJpaEntity publisherJpaEntity = new PublisherJpaEntity(
                1L,
                "Editorial Sudamericana",
                slug
        );

        //PublisherEntity expected = publisherEntities.getFirst();
        //PublisherJpaEntity jpaEntity = PublisherMapper.INSTANCE.publisherEntityToPublisherJpaEntity(expected);
        //when(publisherDao.findBySlug(slug)).thenReturn(Optional.of(jpaEntity));
        when(publisherDao.findBySlug(slug)).thenReturn(Optional.of(publisherJpaEntity));

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

    @Test
    @DisplayName("Test findAll method returns list of PublisherEntity")
    void testFindAll() {
         List<PublisherJpaEntity> jpaEntities = List.of(
                 new PublisherJpaEntity(1L, "Editorial Sudamericana", "editorial-sudamericana"),
                 new PublisherJpaEntity(2L, "Penguin Random House", "penguin-random-house")
         );
         List<PublisherEntity> expected = List.of(
                 new PublisherEntity(1L, "Editorial Sudamericana", "editorial-sudamericana"),
                 new PublisherEntity(2L, "Penguin Random House", "penguin-random-house")
         );


        when(publisherDao.findAll(0, 10)).thenReturn(jpaEntities);

        List<PublisherEntity> result = publisherRepositoryImpl.findAll(0, 10);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(expected.size(), result.size()),
                () -> assertEquals(expected.getFirst().name(), result.getFirst().name()),
                () -> assertEquals(expected.get(1).slug(), result.get(1).slug())
        );
    }
}