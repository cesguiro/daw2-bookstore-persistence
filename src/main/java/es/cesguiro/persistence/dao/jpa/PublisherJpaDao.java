package es.cesguiro.persistence.dao.jpa;

import es.cesguiro.persistence.dao.jpa.entity.PublisherJpaEntity;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public interface PublisherJpaDao extends GenericJpaDao<PublisherJpaEntity> {

    Optional<PublisherJpaEntity> findBySlug(String slug);
}
