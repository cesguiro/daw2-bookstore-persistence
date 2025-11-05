package es.cesguiro.persistence.repository;


import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.persistence.dao.jpa.PublisherJpaDao;
import es.cesguiro.persistence.dao.jpa.entity.PublisherJpaEntity;
import es.cesguiro.persistence.util.InstancioModel;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.instancio.Select.field;
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
        PublisherJpaEntity publisherJpaEntity = Instancio.of(InstancioModel.PUBLISHER_JPA_ENTITY_MODEL)
                .withSeed(10)
                .create();
        PublisherEntity expected = Instancio.of(InstancioModel.PUBLISHER_ENTITY_MODEL)
                .withSeed(10)
                .create();
        when(publisherDao.findBySlug(any())).thenReturn(Optional.of(publisherJpaEntity));

        Optional<PublisherEntity> result = publisherRepositoryImpl.findBySlug("some-slug");
        assertAll(
                () -> assertTrue(result.isPresent()),
                () -> assertEquals(expected.name(), result.get().name(), "Names should match"),
                () -> assertEquals(expected.slug(), result.get().slug(), "Slugs should match")
        );
    }

    @Test
    @DisplayName("Test findBySlug method returns Optional.empty()")
    void testFindBySlugEmpty() {
        when(publisherDao.findBySlug(any())).thenReturn(Optional.empty());
        assertTrue(publisherRepositoryImpl.findBySlug("non-existing-slug").isEmpty());
    }

    @Test
    @DisplayName("Test findAll method returns list of PublisherEntity")
    void testFindAll() {
         List<PublisherJpaEntity> jpaEntities = Instancio.ofList(InstancioModel.PUBLISHER_JPA_ENTITY_MODEL)
                .size(2)
                .withSeed(20)
                .create();
         List<PublisherEntity> expected = Instancio.ofList(InstancioModel.PUBLISHER_ENTITY_MODEL)
                .size(2)
                .withSeed(20)
                .create();


        when(publisherDao.findAll(0, 10)).thenReturn(jpaEntities);

        List<PublisherEntity> result = publisherRepositoryImpl.findAll(0, 10);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(expected.size(), result.size(), "List sizes should match"),
                () -> assertEquals(expected.getFirst().name(), result.getFirst().name(), "First entity names should match"),
                () -> assertEquals(expected.getFirst().slug(), result.getFirst().slug(), "First entity slugs should match"),
                () -> assertEquals(expected.getLast().name(), result.getLast().name(), "Last entity names should match"),
                () -> assertEquals(expected.getLast().slug(), result.getLast().slug(), "Last entity slugs should match")
        );
    }
}