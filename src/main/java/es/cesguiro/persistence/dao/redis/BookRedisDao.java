package es.cesguiro.persistence.dao.redis;

import es.cesguiro.persistence.dao.redis.entity.BookRedisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRedisDao extends CrudRepository<BookRedisEntity, Long> {

    Optional<BookRedisEntity> findByIsbn(String isbn);

}
