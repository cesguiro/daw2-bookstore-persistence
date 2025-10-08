package es.cesguiro.persistence.dao.jpa;

import es.cesguiro.domain.model.Page;

import java.util.List;
import java.util.Optional;

public interface GenericJpaDao<T> {

    List<T> findAll(int page, int size);
    Optional<T> findById(Long id);
    T insert(T entity);
    T update(T entity);
    void deleteById(Long id);
    long count();
}
