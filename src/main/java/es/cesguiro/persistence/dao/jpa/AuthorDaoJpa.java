package es.cesguiro.persistence.dao.jpa;

import es.cesguiro.domain.model.Page;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.persistence.dao.AuthorDao;
import es.cesguiro.persistence.dao.jpa.entity.AuthorJpaEntity;
import es.cesguiro.persistence.dao.jpa.mapper.AuthorMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<AuthorEntity> findAll(int page, int size) {
        return null;
    }

    @Override
    public Optional<AuthorEntity> findById(Long id) {
        AuthorEntity authorEntity = AuthorMapper.INSTANCE
                .authorJpaEntityToAuthorEntity(entityManager.find(AuthorJpaEntity.class, id));
        return Optional.ofNullable(authorEntity);
    }

    @Override
    public AuthorEntity insert(AuthorEntity entity) {
        return null;
    }

    @Override
    public AuthorEntity update(AuthorEntity entity) {
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
    public Optional<AuthorEntity> findBySlug(String slug) {
        return Optional.empty();
    }
}
