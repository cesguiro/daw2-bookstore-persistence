package es.cesguiro.persistence.repository;

import es.cesguiro.domain.model.Page;
import es.cesguiro.domain.repository.BookRepository;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.service.dto.BookDto;
import es.cesguiro.persistence.dao.BookDao;
import es.cesguiro.persistence.dao.jpa.entity.BookJpaEntity;
import es.cesguiro.persistence.dao.jpa.mapper.BookMapper;
import es.cesguiro.persistence.dao.redis.BookRedisDao;

import java.util.Optional;

public class BookRepositoryImpl implements BookRepository {

    private final BookDao bookDao;
    private final BookRedisDao bookRedisDao;

    public BookRepositoryImpl(BookDao bookDao, BookRedisDao bookRedisDao) {
        this.bookDao = bookDao;
        this.bookRedisDao = bookRedisDao;
    }

    @Override
    public Page<BookEntity> findAll(int page, int size) {
        return bookDao.findAll(page, size);
    }

    @Override
    public Optional<BookEntity> findByIsbn(String isbn) {

        // 1️⃣ Buscar en Redis
        /*Optional<BookJpaEntity> bookFromCache = bookRedisDao.findByIsbn(isbn);

        if (bookFromCache.isPresent()) {
            System.out.println("✅ Cache HIT en Redis");
            return Optional.of(BookMapper.INSTANCE.fromBookJpaEntityToBookEntity(bookFromCache.get()));
        }

        System.out.println("❌ Cache MISS — buscando en la base de datos");*/

        // 2️⃣ Buscar en la base de datos
        Optional<BookEntity> bookFromDb = bookDao.findByIsbn(isbn);

        // 3️⃣ Si se encuentra en BD, guardar en Redis para la próxima vez
        /*bookFromDb.ifPresent(book -> {
            bookRedisDao.save(BookMapper.INSTANCE.fromBookEntityToBookJpaEntity(book));
            System.out.println("📦 Guardado en Redis para la próxima vez");
        });*/

        // 4️⃣ Devolver mapeado al modelo de dominio
        return bookFromDb;
    }

    @Override
    public BookEntity save(BookEntity bookEntity) {
        // Podríamos hacer bookDao.save(bookEntity) y que el DAO se encargue de decidir si es insert o update, con merge o persist, por ejemplo.
        if(bookEntity.id() == null) {
            return bookDao.insert(bookEntity);
        }
        return bookDao.update(bookEntity);
    }

    @Override
    public Optional<BookEntity> findById(Long id) {
        return bookDao.findById(id);
    }

    @Override
    public void deleteByIsbn(String isbn) {
        bookDao.deleteByIsbn(isbn);
    }
}
