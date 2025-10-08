package es.cesguiro.persistence.repository;

import es.cesguiro.domain.model.Page;
import es.cesguiro.domain.repository.BookRepository;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.persistence.dao.jpa.BookJpaDao;
import es.cesguiro.persistence.dao.jpa.entity.BookJpaEntity;
import es.cesguiro.persistence.dao.redis.BookRedisDao;
import es.cesguiro.persistence.repository.mapper.BookMapper;

import java.util.List;
import java.util.Optional;

public class BookRepositoryImpl implements BookRepository {

    private final BookJpaDao bookJpaDao;
    private final BookRedisDao bookRedisDao;

    public BookRepositoryImpl(BookJpaDao bookJpaDao, BookRedisDao bookRedisDao) {
        this.bookJpaDao = bookJpaDao;
        this.bookRedisDao = bookRedisDao;
    }

    @Override
    public Page<BookEntity> findAll(int page, int size) {
        List<BookEntity> content = bookJpaDao.findAll(page, size).stream()
                .map(BookMapper.INSTANCE::fromBookJpaEntityToBookEntity)
                .toList();
        long totalElements = bookJpaDao.count();
        return new Page<>(content, page, size, totalElements);
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
        Optional<BookEntity> bookFromDb = bookJpaDao.findByIsbn(isbn)
                .map(BookMapper.INSTANCE::fromBookJpaEntityToBookEntity);

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
        BookJpaEntity bookJpaEntity = BookMapper.INSTANCE.fromBookEntityToBookJpaEntity(bookEntity);
        if(bookEntity.id() == null) {
            return BookMapper.INSTANCE.fromBookJpaEntityToBookEntity(bookJpaDao.insert(bookJpaEntity));
        }
        return BookMapper.INSTANCE.fromBookJpaEntityToBookEntity(bookJpaDao.update(bookJpaEntity));
    }

    @Override
    public Optional<BookEntity> findById(Long id) {
        return bookJpaDao.findById(id)
                .map(BookMapper.INSTANCE::fromBookJpaEntityToBookEntity);
    }

    @Override
    public void deleteByIsbn(String isbn) {
        bookJpaDao.deleteByIsbn(isbn);
    }
}
