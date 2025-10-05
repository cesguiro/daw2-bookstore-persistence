package es.cesguiro.persistence.dao;

import es.cesguiro.domain.model.Page;

import java.util.Optional;

public interface GenericDao<T> {

    Page<T> findAll(int page, int size);
    Optional<T> findById(Long id);
    T insert(T entity);
    T update(T entity);
    void deleteById(Long id);
    long count();
}
