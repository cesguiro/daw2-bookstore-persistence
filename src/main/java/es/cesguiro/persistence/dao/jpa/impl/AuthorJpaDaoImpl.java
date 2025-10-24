package es.cesguiro.persistence.dao.jpa.impl;

import es.cesguiro.persistence.dao.jpa.AuthorJpaDao;
import es.cesguiro.persistence.dao.jpa.entity.AuthorJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

public class AuthorJpaDaoImpl implements AuthorJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AuthorJpaEntity> findAll(int page, int size) {
        return null;
    }

    @Override
    public Optional<AuthorJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(AuthorJpaEntity.class, id));
    }

    @Override
    public AuthorJpaEntity insert(AuthorJpaEntity jpaEntity) {
        return null;
    }

    @Override
    public AuthorJpaEntity update(AuthorJpaEntity jpaEntity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public Optional<AuthorJpaEntity> findBySlug(String slug) {
        return Optional.empty();
    }
}
