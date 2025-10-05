package es.cesguiro.persistence.dao.redis;

import es.cesguiro.persistence.dao.jpa.entity.AuthorJpaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRedisDao extends CrudRepository<AuthorJpaEntity, Long> {
}
