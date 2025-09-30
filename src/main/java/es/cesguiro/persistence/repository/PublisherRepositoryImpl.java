package es.cesguiro.persistence.repository;

import es.cesguiro.domain.repository.PublisherRepository;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.persistence.dao.PublisherDao;

import java.util.Optional;

public class PublisherRepositoryImpl implements PublisherRepository {

    private final PublisherDao publisherDao;

    public PublisherRepositoryImpl(PublisherDao publisherDao) {
        this.publisherDao = publisherDao;
    }

    @Override
    public Optional<PublisherEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<PublisherEntity> findBySlug(String slug) {
        return publisherDao.findBySlug(slug);
    }

    @Override
    public PublisherEntity save(PublisherEntity publisherEntity) {
        return null;
    }
}
