package es.cesguiro.persistence.dao.jpa;

import es.cesguiro.persistence.dao.jpa.entity.BookJpaEntity;

import java.util.Optional;

public interface BookJpaDao extends GenericJpaDao<BookJpaEntity> {

    Optional<BookJpaEntity> findByIsbn(String isbn);
    void deleteByIsbn(String isbn);
}
