package es.cesguiro.persistence.dao.jpa.impl;

import es.cesguiro.persistence.dao.jpa.PublisherJpaDao;
import es.cesguiro.persistence.dao.jpa.entity.PublisherJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

public class PublisherJpaDaoJpaImpl implements PublisherJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PublisherJpaEntity> findBySlug(String slug) {
        String sql = "SELECT p FROM PublisherJpaEntity p WHERE p.slug = :slug";
        try {
            PublisherJpaEntity publisherJpaEntity = entityManager.createQuery(sql, PublisherJpaEntity.class)
                    .setParameter("slug", slug)
                    .getSingleResult();
            return Optional.of(publisherJpaEntity);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<PublisherJpaEntity> findAll(int page, int size) {
        return null;
    }

    @Override
    public Optional<PublisherJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(PublisherJpaEntity.class, id));
    }

    @Override
    public PublisherJpaEntity insert(PublisherJpaEntity publisherJpaEntity) {
        return null;
    }

    @Override
    public PublisherJpaEntity update(PublisherJpaEntity publisherJpaEntity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {}

    @Override
    public long count() {
        return 0;
    }

    /********** CriteriaBuilder version **********/
    public Optional<PublisherJpaEntity> findBySlugCb(String slug) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PublisherJpaEntity> criteriaQuery = builder.createQuery(PublisherJpaEntity.class);
        Root<PublisherJpaEntity> root = criteriaQuery.from(PublisherJpaEntity.class);
        criteriaQuery.select(builder.construct(PublisherJpaEntity.class,
                root.get("id"),
                root.get("name"),
                root.get("slug")))
                .where(builder.equal(root.get("slug"), slug));
        try {
            return Optional.ofNullable(entityManager.createQuery(criteriaQuery).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
