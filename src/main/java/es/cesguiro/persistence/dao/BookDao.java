package es.cesguiro.persistence.dao;

import es.cesguiro.domain.model.Page;
import es.cesguiro.domain.repository.entity.BookEntity;

public interface BookDao {

    BookEntity insert(BookEntity bookEntity);
    BookEntity update(BookEntity bookEntity);
    Page<BookEntity> findAll(int page, int size);
}
