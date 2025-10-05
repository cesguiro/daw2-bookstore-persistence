package es.cesguiro.persistence.dao;

import es.cesguiro.domain.model.Page;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.service.dto.BookDto;

import java.util.Optional;

public interface BookDao extends GenericDao<BookEntity> {

    Optional<BookEntity> findByIsbn(String isbn);
    void deleteByIsbn(String isbn);
}
