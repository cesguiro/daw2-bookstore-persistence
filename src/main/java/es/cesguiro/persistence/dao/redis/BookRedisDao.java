package es.cesguiro.persistence.dao.redis;

import es.cesguiro.persistence.dao.jpa.entity.BookJpaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRedisDao extends CrudRepository<BookJpaEntity, Long> {

    public Optional<BookJpaEntity> findByIsbn(String isbn);
}
