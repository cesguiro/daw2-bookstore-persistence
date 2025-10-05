package es.cesguiro.persistence.dao;

import es.cesguiro.domain.repository.entity.PublisherEntity;

import java.util.Optional;

public interface PublisherDao extends GenericDao<PublisherEntity> {

    Optional<PublisherEntity> findBySlug(String slug);
}
