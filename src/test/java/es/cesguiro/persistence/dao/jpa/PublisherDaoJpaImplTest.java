package es.cesguiro.persistence.dao.jpa;

import es.cesguiro.persistence.TestConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;


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
    }


    @Test
    @DisplayName("Test findBySlug method returns Optional<PublisherEntity>")
    void testFindBySlug() {
        PublisherEntity expected = publisherEntities.getFirst();
        Optional<PublisherEntity> result = publisherDao.findBySlug(expected.slug());
        assertAll(
                () -> assertTrue(result.isPresent()),
                () -> assertEquals(expected.id(), result.get().id()),
                () -> assertEquals(expected.slug(), result.get().slug()),
                () -> assertEquals(expected.name(), result.get().name())
        );
    }

    @Test
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