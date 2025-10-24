package es.cesguiro.persistence.dao.jpa.impl;

import es.cesguiro.domain.exception.ResourceNotFoundException;
import es.cesguiro.persistence.dao.jpa.BookJpaDao;
import es.cesguiro.persistence.dao.jpa.entity.BookJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class BookJpaDaoImpl implements BookJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BookJpaEntity insert(BookJpaEntity jpaEntity) {
        entityManager.persist(jpaEntity);
        return jpaEntity;
    }

    @Override
    public BookJpaEntity update(BookJpaEntity jpaEntity) {
        BookJpaEntity managed = entityManager.find(BookJpaEntity.class, jpaEntity.getId());
        if(managed == null) {
            throw new ResourceNotFoundException("Book with id " + jpaEntity.getId() + " not found");
        }
        managed.getBookAuthors().clear();
        entityManager.flush();
        return entityManager.merge(jpaEntity);
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
    public List<BookJpaEntity> findAll(int page, int size) {
        int pageIndex = Math.max(page - 1, 0);

        String sql = "SELECT b FROM BookJpaEntity b ORDER BY b.id";
        TypedQuery<BookJpaEntity> bookJpaEntityPage = entityManager
                .createQuery(sql, BookJpaEntity.class)
                .setFirstResult(pageIndex * size)
                .setMaxResults(size);

        return bookJpaEntityPage.getResultList();
    }

    @Override
    public Optional<BookJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(BookJpaEntity.class, id));
    }

    @Override
    public Optional<BookJpaEntity> findByIsbn(String isbn) {
        String sql = "SELECT b FROM BookJpaEntity b WHERE b.isbn = :isbn";
        try {
            return Optional.of(entityManager.createQuery(sql, BookJpaEntity.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<BookJpaEntity> findByIsbnNative(String isbn) {
        String sql = "SELECT * FROM books b WHERE b.isbn = :isbn";
        try {
            return Optional.of((BookJpaEntity) entityManager.createNativeQuery(sql, BookJpaEntity.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult());
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
