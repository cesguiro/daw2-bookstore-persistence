package es.cesguiro.persistence.dao;

import es.cesguiro.domain.repository.entity.PublisherEntity;

import java.util.Optional;

public interface PublisherDao {

    Optional<PublisherEntity> findBySlug(String slug);
    Optional<PublisherEntity> findById(Long id);
}
