package es.cesguiro.persistence.dao.jpa;

import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.persistence.dao.BookDao;
import es.cesguiro.persistence.dao.jpa.entity.BookJpaEntity;
import es.cesguiro.persistence.dao.jpa.mapper.BookMapper;
import jakarta.persistence.EntityManager;

public class BookDaoJpa implements BookDao {

    private final EntityManager entityManager;

    public BookDaoJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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
}
