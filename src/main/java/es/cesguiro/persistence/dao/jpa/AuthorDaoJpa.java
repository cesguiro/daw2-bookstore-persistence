package es.cesguiro.persistence.dao.jpa;

import es.cesguiro.persistence.dao.AuthorDao;
import jakarta.persistence.EntityManager;

public class AuthorDaoJpa implements AuthorDao {

    private final EntityManager entityManager;

    public AuthorDaoJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
