package es.cesguiro.persistence.dao.jpa.mapper;

import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.persistence.dao.jpa.entity.AuthorJpaEntity;
import org.mapstruct.Mapper;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(AuthorMapper.class);

    AuthorJpaEntity authorEntityToAuthorJpaEntity(AuthorEntity authorEntity);

    AuthorEntity authorJpaEntityToAuthorEntity(AuthorJpaEntity authorJpaEntity);
}
