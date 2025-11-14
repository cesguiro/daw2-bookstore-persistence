package es.cesguiro.persistence.dao.jpa.impl;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import es.cesguiro.persistence.annotation.DaoTest;
import es.cesguiro.persistence.dao.jpa.PublisherJpaDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.cesguiro.persistence.dao.jpa.entity.PublisherJpaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DaoTest
class PublisherDaoJpaImplShould extends BaseJpaDaoTest<PublisherJpaDao> {

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
    @DataSet(value= "adapters/data/publishers.json")
    @ExpectedDataSet(value= "adapters/data/publishers-after-insert.json", ignoreCols = {"id"})
    void insert_publisher_correctly() {
        PublisherJpaEntity publisherToInsert = new PublisherJpaEntity(null, "new publisher", "new-publisher");
        dao.insert(publisherToInsert);
        flushAndCommitForDbRider();
    }

    @Test
    @DataSet(value= "adapters/data/publishers.json")
    @ExpectedDataSet(value= "adapters/data/publishers-after-update.json")
    void update_publisher_correctly() {
        PublisherJpaEntity publisherToUpdate = new PublisherJpaEntity(1L, "updated publisher", "updated-publisher");
        dao.update(publisherToUpdate);
        flushAndCommitForDbRider();
    }

}