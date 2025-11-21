package es.cesguiro.persistence.dao.jpa.impl;


import es.cesguiro.persistence.annotation.DaoTest;
import es.cesguiro.persistence.dao.jpa.PublisherJpaDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.cesguiro.persistence.dao.jpa.entity.PublisherJpaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DaoTest
class PublisherDaoJpaImplShould extends BaseJpaDaoTest<PublisherJpaDao> {

    /*@Autowired
    private PublisherJpaDao dao;*/

    @Test
    void container_is_running() {
        assertThat(mariaDBContainer.isRunning()).isTrue();
    }

    @Test
    //@DataSet(value= "adapters/data/publishers.json")
    void return_publisher_when_slug_exists() {
        Optional<PublisherJpaEntity> result =  dao.findBySlug("anagrama");

        assertThat(result)
                .hasValueSatisfying(publisher -> assertThat(publisher)
                        .extracting(PublisherJpaEntity::getName, PublisherJpaEntity::getSlug)
                        .containsExactly("Anagrama", "anagrama")
                );
    }

    @Test
    //@DataSet(value= "adapters/data/publishers.json")
    void return_empty_when_slug_does_not_exist() {
        String slug = "non-existing-slug";

        Optional<PublisherJpaEntity> result = dao.findBySlug(slug);

        assertThat(result).isNotPresent();

    }

    @Test
    /*@DataSet(value= "adapters/data/publishers.json")
    @ExpectedDataSet(value= "adapters/data/publishers-after-insert.json", ignoreCols = {"id"})*/
    void insert_publisher_correctly() {
        PublisherJpaEntity publisherToInsert = new PublisherJpaEntity(null, "new publisher", "new-publisher");
        dao.insert(publisherToInsert);

        Optional<PublisherJpaEntity> result = dao.findBySlug("new-publisher");

        assertThat(result)
                .isPresent()
                .hasValueSatisfying(publisher -> assertThat(publisher)
                .extracting(PublisherJpaEntity::getName, PublisherJpaEntity::getSlug)
                .containsExactly("new publisher", "new-publisher"));
        //flushAndCommitForDbRider();
    }

    @Test
    /*@DataSet(value= "adapters/data/publishers.json")
    @ExpectedDataSet(value= "adapters/data/publishers-after-update.json")*/
    void update_publisher_correctly() {
        PublisherJpaEntity publisherToUpdate = new PublisherJpaEntity(1L, "updated publisher", "updated-publisher");
        dao.update(publisherToUpdate);

        Optional <PublisherJpaEntity> result = dao.findBySlug("updated-publisher");
        assertThat(result)
                .isPresent()
                .hasValueSatisfying(publisher -> assertThat(publisher)
                        .extracting(PublisherJpaEntity::getName, PublisherJpaEntity::getSlug)
                        .containsExactly("updated publisher", "updated-publisher"));
    }

}