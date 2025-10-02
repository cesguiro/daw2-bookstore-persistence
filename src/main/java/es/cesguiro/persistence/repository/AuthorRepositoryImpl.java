package es.cesguiro.persistence.repository;

import es.cesguiro.domain.repository.AuthorRepository;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.persistence.dao.AuthorDao;

import java.util.Optional;

public class AuthorRepositoryImpl implements AuthorRepository {

    private final AuthorDao authorDao;

    public AuthorRepositoryImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return null;
    }

    @Override
    public Optional<AuthorEntity> findById(Long id) {
        return authorDao.findById(id);
    }
}
