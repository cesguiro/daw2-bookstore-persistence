package es.cesguiro.persistence.dao.jpa;

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
    public Optional<AuthorEntity> findById(Long id) {
        AuthorEntity authorEntity = AuthorMapper.INSTANCE
                .authorJpaEntityToAuthorEntity(entityManager.find(AuthorJpaEntity.class, id));
        return Optional.ofNullable(authorEntity);
    }
}
