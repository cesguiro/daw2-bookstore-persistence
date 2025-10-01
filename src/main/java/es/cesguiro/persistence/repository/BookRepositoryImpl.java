package es.cesguiro.persistence.repository;

import es.cesguiro.domain.model.Page;
import es.cesguiro.domain.repository.BookRepository;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.persistence.dao.BookDao;

import java.util.Optional;

public class BookRepositoryImpl implements BookRepository {

    private final BookDao bookDao;

    public BookRepositoryImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Page<BookEntity> findAll(int page, int size) {
        return bookDao.findAll(page, size);
    }

    @Override
    public Optional<BookEntity> findByIsbn(String isbn) {
        return Optional.empty();
    }

    @Override
    public BookEntity save(BookEntity bookEntity) {
        // Podríamos hacer bookDao.save(bookEntity) y que el DAO se encargue de decidir si es insert o update, con merge o persist, por ejemplo.
        if(bookEntity.id() == null) {
            return bookDao.insert(bookEntity);
        }
        return bookDao.update(bookEntity);
    }
}
