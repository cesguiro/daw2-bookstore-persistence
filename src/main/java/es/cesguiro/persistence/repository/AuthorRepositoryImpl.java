package es.cesguiro.persistence.repository;

import es.cesguiro.domain.repository.AuthorRepository;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.persistence.dao.jpa.AuthorJpaDao;
import es.cesguiro.persistence.repository.mapper.AuthorMapper;

import java.util.Optional;

public class AuthorRepositoryImpl implements AuthorRepository {

    private final AuthorJpaDao authorJpaDao;

    public AuthorRepositoryImpl(AuthorJpaDao authorJpaDao) {
        this.authorJpaDao = authorJpaDao;
    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return null;
    }

    @Override
    public Optional<AuthorEntity> findById(Long id) {
        return authorJpaDao.findById(id)
                .map(AuthorMapper.INSTANCE::authorJpaEntityToAuthorEntity);
    }
}
