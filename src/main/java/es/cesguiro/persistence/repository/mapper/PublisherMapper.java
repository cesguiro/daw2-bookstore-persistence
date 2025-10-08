package es.cesguiro.persistence.repository.mapper;

import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.persistence.dao.jpa.entity.PublisherJpaEntity;
import es.cesguiro.persistence.dao.redis.entity.PublisherRedisEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublisherMapper {

    PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    PublisherJpaEntity publisherEntityToPublisherJpaEntity(PublisherEntity publisherEntity);

    PublisherEntity publisherJpaEntityToPublisherEntity(PublisherJpaEntity publisherJpaEntity);

    PublisherRedisEntity publisherEntityToPublisherRedisEntity(PublisherEntity publisherEntity);

    PublisherEntity publisherRedisEntityToPublisherEntity(PublisherRedisEntity publisherRedisEntity);

}
