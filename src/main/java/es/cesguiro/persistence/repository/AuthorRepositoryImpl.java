package es.cesguiro.persistence.repository;

import es.cesguiro.domain.repository.AuthorRepository;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.persistence.dao.AuthorDao;

public class AuthorRepositoryImpl implements AuthorRepository {

    private final AuthorDao authorDao;

    public AuthorRepositoryImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return null;
    }
}
