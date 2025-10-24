package es.cesguiro.persistence.dao.jpa.impl;

import es.cesguiro.persistence.dao.jpa.AuthorJpaDao;
import es.cesguiro.persistence.dao.jpa.entity.AuthorJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AuthorJpaDaoImplTest {

    private final AuthorJpaDao authorJpaDao = new AuthorJpaDaoImpl();

    @Test
    @DisplayName("Insert a new author and verify it is saved correctly")
    void testInsertAuthor() {
        AuthorJpaEntity newAuthor = new AuthorJpaEntity(
                null,
                "Test Author",
                "test-nationality",
                "test-biography-es",
                "test-biography-en",
                1970,
                null,
                "test-author"
        );
        AuthorJpaEntity authorSaved = authorJpaDao.insert(newAuthor);
        assertNotNull(authorSaved.getId(), "Author ID should not be null after insertion");
    }

}