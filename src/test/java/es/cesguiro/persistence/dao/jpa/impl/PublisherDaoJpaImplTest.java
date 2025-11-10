package es.cesguiro.persistence.dao.jpa.impl;

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

import static org.assertj.core.api.Assertions.assertThat;


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

        Optional<PublisherJpaEntity> result = publisherDao.findBySlug(slug);

        assertThat(result)
                .hasValueSatisfying(publisher -> assertThat(publisher)
                        .extracting(PublisherJpaEntity::getSlug, PublisherJpaEntity::getName)
                        .containsExactly(slug, "HarperCollins")
                );
    }

    @Test
    @DisplayName("Test findBySlug method with non-existing slug")
    void testFindBySlugCb() {
        String slug = "non-existing-slug";
        Optional<PublisherJpaEntity> result = publisherDao.findBySlug(slug);

        assertThat(result).isNotPresent();
    }
}