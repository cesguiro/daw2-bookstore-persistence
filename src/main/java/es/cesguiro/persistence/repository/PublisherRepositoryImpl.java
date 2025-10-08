package es.cesguiro.persistence.repository;

import es.cesguiro.domain.repository.PublisherRepository;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.persistence.dao.jpa.PublisherJpaDao;
import es.cesguiro.persistence.repository.mapper.PublisherMapper;

import java.util.Optional;

public class PublisherRepositoryImpl implements PublisherRepository {

    private final PublisherJpaDao publisherDao;

    public PublisherRepositoryImpl(PublisherJpaDao publisherDao) {
        this.publisherDao = publisherDao;
    }

    @Override
    public Optional<PublisherEntity> findById(Long id) {
        return publisherDao.findById(id)
                .map(PublisherMapper.INSTANCE::publisherJpaEntityToPublisherEntity);
    }

    @Override
    public Optional<PublisherEntity> findBySlug(String slug) {
        return publisherDao.findBySlug(slug)
                .map(PublisherMapper.INSTANCE::publisherJpaEntityToPublisherEntity);
    }

    @Override
    public PublisherEntity save(PublisherEntity publisherEntity) {
        return null;
    }
}
