package es.cesguiro.persistence.dao.jpa;

import es.cesguiro.persistence.dao.jpa.entity.AuthorJpaEntity;

import java.util.Optional;

public interface AuthorJpaDao extends GenericJpaDao<AuthorJpaEntity> {

    Optional<AuthorJpaEntity> findBySlug(String slug);
}
