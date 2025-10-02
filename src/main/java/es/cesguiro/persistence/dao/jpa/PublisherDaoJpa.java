package es.cesguiro.persistence.dao.jpa;

import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.persistence.dao.PublisherDao;
import es.cesguiro.persistence.dao.jpa.entity.PublisherJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.Optional;

public class PublisherDaoJpa implements PublisherDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PublisherEntity> findBySlug(String slug) {
        String sql = "SELECT " +
                "new es.cesguiro.domain.repository.entity.PublisherEntity(" +
                "p.id, p.name, p.slug) " +
                "FROM PublisherJpaEntity p " +
                "WHERE p.slug = :slug";
        try {
            PublisherEntity publisherEntity = entityManager.createQuery(sql, PublisherEntity.class)
                    .setParameter("slug", slug)
                    .getSingleResult();
            return Optional.of(publisherEntity);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<PublisherEntity> findById(Long id) {
        PublisherEntity publisherEntity = entityManager.find(PublisherEntity.class, id);
        return Optional.ofNullable(publisherEntity);
    }

    /********** CriteriaBuilder version **********/
    public Optional<PublisherEntity> findBySlugCb(String slug) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PublisherEntity> criteriaQuery = builder.createQuery(PublisherEntity.class);
        Root<PublisherJpaEntity> root = criteriaQuery.from(PublisherJpaEntity.class);
        criteriaQuery.select(builder.construct(PublisherEntity.class,
                root.get("id"),
                root.get("name"),
                root.get("slug")))
                .where(builder.equal(root.get("slug"), slug));
        try {
            PublisherEntity publisherEntity = entityManager.createQuery(criteriaQuery)
                    .getSingleResult();
            return Optional.of(publisherEntity);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
