package es.cesguiro.persistence.dao.jpa.impl;

import es.cesguiro.persistence.dao.jpa.PublisherJpaDao;
import es.cesguiro.persistence.dao.jpa.entity.PublisherJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public class PublisherJpaDaoImpl implements PublisherJpaDao {

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
        int pageIndex = Math.max(page - 1, 0);

        String sql = "SELECT p FROM PublisherJpaEntity p ORDER BY p.id";
        return entityManager.createQuery(sql, PublisherJpaEntity.class)
                .setFirstResult(pageIndex * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public Optional<PublisherJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(PublisherJpaEntity.class, id));
    }

    @Override
    public PublisherJpaEntity insert(PublisherJpaEntity jpaEntity) {
        entityManager.persist(jpaEntity);
        return jpaEntity;
    }

    @Override
    public PublisherJpaEntity update(PublisherJpaEntity jpaEntity) {
        return entityManager.merge(jpaEntity);
    }

    @Override
    public void deleteById(Long id) {}

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(p) FROM PublisherJpaEntity p", Long.class)
                .getSingleResult();
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
