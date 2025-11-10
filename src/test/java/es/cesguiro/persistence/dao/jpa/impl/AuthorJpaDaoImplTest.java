package es.cesguiro.persistence.dao.jpa.impl;

import es.cesguiro.persistence.TestConfig;
import es.cesguiro.persistence.dao.jpa.AuthorJpaDao;
import es.cesguiro.persistence.dao.jpa.entity.AuthorJpaEntity;
import es.cesguiro.persistence.util.InstancioModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
class AuthorJpaDaoImplTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AuthorJpaDao authorJpaDao;

    @Test
    @DisplayName("Insert should persist AuthorJpaEntity and assign an ID")
    void testInsertAuthor() {
        AuthorJpaEntity newAuthor = Instancio.of(InstancioModel.AUTHOR_JPA_ENTITY_MODEL)
                .set(field(AuthorJpaEntity::getId), null)
                .create();
        AuthorJpaEntity result = authorJpaDao.insert(newAuthor);

        assertThat(result)
                .isNotNull()
                .extracting(AuthorJpaEntity::getId)
                .isNotNull();
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(newAuthor);

    }

}