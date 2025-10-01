package es.cesguiro.persistence.dao.jpa;

import es.cesguiro.persistence.dao.AuthorDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private EntityManager entityManager;

}
