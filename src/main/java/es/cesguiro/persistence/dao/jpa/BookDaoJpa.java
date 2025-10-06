package es.cesguiro.persistence.dao.jpa;

import es.cesguiro.domain.model.Page;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.persistence.dao.BookDao;
import es.cesguiro.persistence.dao.jpa.entity.BookJpaEntity;
import es.cesguiro.persistence.dao.jpa.mapper.BookMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BookEntity insert(BookEntity bookEntity) {
        BookJpaEntity bookJpaEntity = BookMapper.INSTANCE.fromBookEntityToBookJpaEntity(bookEntity);
        entityManager.persist(bookJpaEntity);
        //entityManager.flush(); // Escribe los cambios pendientes en la base de datos pero no termina la transacción
        //Deberíamos usar @Transactional en el servicio que use este DAO, o hacerlo de forma manual para no romper la arquitectura limpia
        return BookMapper.INSTANCE.fromBookJpaEntityToBookEntity(bookJpaEntity);
    }

    @Override
    public BookEntity update(BookEntity bookEntity) {
        BookJpaEntity bookJpaEntity = BookMapper.INSTANCE.fromBookEntityToBookJpaEntity(bookEntity);
        entityManager.merge(bookJpaEntity);
        return BookMapper.INSTANCE.fromBookJpaEntityToBookEntity(bookJpaEntity);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(entityManager.find(BookJpaEntity.class, id));
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(b) FROM BookJpaEntity b", Long.class)
                .getSingleResult();
    }

    @Override
    public Page<BookEntity> findAll(int page, int size) {
        int pageIndex = Math.max(page - 1, 0);

        String sql = "SELECT b FROM BookJpaEntity b ORDER BY b.id";
        TypedQuery<BookJpaEntity> bookJpaEntityPage = entityManager
                .createQuery(sql, BookJpaEntity.class)
                .setFirstResult(pageIndex * size)
                .setMaxResults(size);

        List<BookEntity> content = bookJpaEntityPage.getResultList()
                .stream()
                .map(BookMapper.INSTANCE::fromBookJpaEntityToBookEntity)
                .toList();

        return new Page<>(content, page, size, count());
    }

    @Override
    public Optional<BookEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(BookJpaEntity.class, id))
                .map(BookMapper.INSTANCE::fromBookJpaEntityToBookEntity);
    }

    @Override
    public Optional<BookEntity> findByIsbn(String isbn) {
        String sql = "SELECT b FROM BookJpaEntity b WHERE b.isbn = :isbn";
        try {
            BookJpaEntity bookJpaEntity = entityManager.createQuery(sql, BookJpaEntity.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult();
            return Optional.of(BookMapper.INSTANCE.fromBookJpaEntityToBookEntity(bookJpaEntity));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteByIsbn(String isbn) {
        entityManager.createQuery("DELETE FROM BookJpaEntity b WHERE b.isbn = :isbn")
                .setParameter("isbn", isbn)
                .executeUpdate();
    }
}
