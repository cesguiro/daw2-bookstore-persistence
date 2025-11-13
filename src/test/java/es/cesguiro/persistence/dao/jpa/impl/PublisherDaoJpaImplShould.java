package es.cesguiro.persistence.dao.jpa.impl;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import es.cesguiro.persistence.annotation.DaoTest;
import es.cesguiro.persistence.dao.jpa.PublisherJpaDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;

import es.cesguiro.persistence.dao.jpa.entity.PublisherJpaEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DaoTest
class PublisherDaoJpaImplShould extends BaseJpaDaoTest<PublisherJpaDao> {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DataSet(value= "adapters/data/publishers.json")
    void return_publisher_when_slug_exists() {
        Optional<PublisherJpaEntity> result =  dao.findBySlug("publisher-name");
        assertThat(result)
                .hasValueSatisfying(publisher -> assertThat(publisher)
                        .extracting(PublisherJpaEntity::getName, PublisherJpaEntity::getSlug)
                        .containsExactly("publisher name", "publisher-name")
                );
    }

    @Test
    @DataSet(value= "adapters/data/publishers.json")
    void return_empty_when_slug_does_not_exist() {
        String slug = "non-existing-slug";

        Optional<PublisherJpaEntity> result = dao.findBySlug(slug);

        assertThat(result).isNotPresent();

    }

    @Test
    @DataSet(value= "adapters/data/publishers.json", transactional = true)
    @ExpectedDataSet(value= "adapters/data/publishers-after-insert.json", ignoreCols = {"id"})
    @Transactional
    void insert_publisher_correctly() {
        PublisherJpaEntity publisherToInsert = new PublisherJpaEntity(null, "new publisher", "new-publisher");
        dao.insert(publisherToInsert);
        //entityManager.flush();
        //flushAndCommitForDbRider();
    }

    @Test
    @DataSet(value= "adapters/data/publishers.json", transactional = true)
    @ExpectedDataSet(value= "adapters/data/publishers-after-update.json")
    @Transactional
    void update_publisher_correctly() {
        PublisherJpaEntity publisherToUpdate = new PublisherJpaEntity(1L, "updated publisher", "updated-publisher");
        dao.update(publisherToUpdate);
        entityManager.flush();
        //flushAndCommitForDbRider();
    }

}