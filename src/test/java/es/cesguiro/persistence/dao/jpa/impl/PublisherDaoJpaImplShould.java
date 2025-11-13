package es.cesguiro.persistence.dao.jpa.impl;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.connection.RiderDataSource;
import com.github.database.rider.junit5.api.DBRider;
import com.github.database.rider.junit5.util.EntityManagerProvider;
import es.cesguiro.persistence.annotation.DaoTest;
import es.cesguiro.persistence.dao.jpa.PublisherJpaDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/*import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.junit5.api.DBRider;*/
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

import javax.sql.DataSource;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DaoTest
class PublisherDaoJpaImplShould {

    /*@PersistenceContext
    private EntityManager entityManager;*/


    private ConnectionHolder connectionHolder = () ->
            EntityManagerProvider.instance("junit5-pu").clear().connection();

    @Autowired
    private PublisherJpaDao publisherDao;

    /*@Autowired
    private Flyway flyway;

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
    }*/



    @Test
    @DataSet(value="adapters/data/publisher.json")
    void found_publisher_by_slug_when_slug_exists() {
        Optional<PublisherJpaEntity> actual =  publisherDao.findBySlug("publisher-name");
        assertThat(actual).isNotEmpty();
    }

    /*@Test
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
    }*/
}