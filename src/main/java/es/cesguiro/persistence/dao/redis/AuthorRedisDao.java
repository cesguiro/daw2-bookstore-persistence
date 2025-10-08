package es.cesguiro.persistence.dao.redis;

import es.cesguiro.persistence.dao.redis.entity.AuthorRedisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRedisDao extends CrudRepository<AuthorRedisEntity, Long> {
}
