package es.cesguiro.persistence.dao;

import es.cesguiro.domain.repository.entity.AuthorEntity;

import java.util.Optional;

public interface AuthorDao extends  GenericDao<AuthorEntity> {

    Optional<AuthorEntity> findBySlug(String slug);
}
