package es.cesguiro.persistence.dao.jpa.impl;

import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.persistence.TestConfig;
import es.cesguiro.persistence.dao.jpa.PublisherJpaDao;
import es.cesguiro.persistence.dao.jpa.entity.PublisherJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PublisherDaoJpaImplTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PublisherJpaDao publisherDao;

    /*@Autowired
    private Flyway flyway;

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
    }*/

    /*private static List<PublisherEntity> publisherEntities;

    @BeforeAll
    static void setUp() {
        PublishersDataLoader publishersDataLoader = new PublishersDataLoader();
        publisherEntities = publishersDataLoader.loadPublisherEntitiesFromCSV();
    }*/


    @Test
    @DisplayName("Test findBySlug method returns Optional<PublisherEntity>")
    void testFindBySlug() {
        String slug = "harpercollins";
        //Optional<PublisherJpaEntity> expected = Optional.of(new PublisherJpaEntity(4L, "HarperCollins", slug));
        Optional<PublisherJpaEntity> result = publisherDao.findBySlug(slug);

        assertThat(result)
                .isPresent()
                .get()
                .extracting(PublisherJpaEntity::getSlug, PublisherJpaEntity::getName)
                .containsExactly(slug, "HarperCollins")
        ;
        /*assertAll(
                () -> assertTrue(result.isPresent()),
                () -> assertEquals(expected.get().getId(), result.get().getId()),
                () -> assertEquals(expected.get().getSlug(), result.get().getSlug()),
                () -> assertEquals(expected.get().getName(), result.get().getName())
        );*/
    }

    /*@Test
    @DisplayName("Test findBySlugCb methor returns Optional<PublisherEntity>")
    void testFindBySlugCb() {
        PublisherEntity expected = publisherEntities.getFirst();
        Optional<PublisherEntity> result = publisherDao.findBySlug(expected.slug());
        assertAll(
                () -> assertTrue(result.isPresent()),
                () -> assertEquals(expected.id(), result.get().id()),
                () -> assertEquals(expected.slug(), result.get().slug()),
                () -> assertEquals(expected.name(), result.get().name())
        );
    }*/
}