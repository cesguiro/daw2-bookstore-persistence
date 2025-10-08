package es.cesguiro.persistence.dao.redis;

import es.cesguiro.persistence.dao.redis.entity.PublisherRedisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRedisDao extends CrudRepository<PublisherRedisEntity, Long> {
}
