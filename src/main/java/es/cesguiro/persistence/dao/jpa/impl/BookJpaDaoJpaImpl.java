package es.cesguiro.persistence.dao.jpa.impl;

import es.cesguiro.persistence.dao.jpa.BookJpaDao;
import es.cesguiro.persistence.dao.jpa.entity.AuthorJpaEntity;
import es.cesguiro.persistence.dao.jpa.entity.BookJpaEntity;
import es.cesguiro.persistence.dao.jpa.entity.PublisherJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class BookJpaDaoJpaImpl implements BookJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BookJpaEntity insert(BookJpaEntity bookJpaEntity) {
        entityManager.persist(bookJpaEntity);
        return bookJpaEntity;
    }

    @Override
    public BookJpaEntity update(BookJpaEntity bookJpaEntity) {
        BookJpaEntity managed = entityManager.find(BookJpaEntity.class, bookJpaEntity.getId());
        if (managed == null) {
            throw new IllegalArgumentException("Book with id " + bookJpaEntity.getId() + " not found");
        }
        managed.setIsbn(bookJpaEntity.getIsbn());
        managed.setTitleEs(bookJpaEntity.getTitleEs());
        managed.setTitleEn(bookJpaEntity.getTitleEn());
        managed.setSynopsisEs(bookJpaEntity.getSynopsisEs());
        managed.setSynopsisEn(bookJpaEntity.getSynopsisEn());
        managed.setBasePrice(bookJpaEntity.getBasePrice());
        managed.setDiscountPercentage(bookJpaEntity.getDiscountPercentage());
        managed.setCover(bookJpaEntity.getCover());
        managed.setPublicationDate(bookJpaEntity.getPublicationDate().toString());

        PublisherJpaEntity publisherRef = bookJpaEntity.getPublisher() != null
                ? entityManager.getReference(PublisherJpaEntity.class, bookJpaEntity.getPublisher())
                :null;
        managed.setPublisher(publisherRef);

        List<AuthorJpaEntity> authorRefs = (bookJpaEntity.getAuthors() != null)
                ? bookJpaEntity.getAuthors().stream()
                    .map(author -> entityManager.getReference(AuthorJpaEntity.class, bookJpaEntity.getAuthors()))
                    .toList()
                : List.of();
        managed.getBookAuthors().clear();
        entityManager.flush();
        managed.setAuthors(authorRefs);

        return managed;
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

    @Override
    public void deleteByIsbn(String isbn) {
        entityManager.createQuery("DELETE FROM BookJpaEntity b WHERE b.isbn = :isbn")
                .setParameter("isbn", isbn)
                .executeUpdate();
    }
}
