package es.cesguiro.persistence.dao;

import es.cesguiro.domain.repository.entity.AuthorEntity;

import java.util.Optional;

public interface AuthorDao {
    Optional<AuthorEntity> findById(Long id);
}
