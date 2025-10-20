package es.cesguiro.persistence.repository.mapper;

import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.persistence.dao.jpa.entity.AuthorJpaEntity;
import es.cesguiro.persistence.dao.jpa.entity.BookJpaEntity;
import es.cesguiro.persistence.dao.redis.entity.BookRedisEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.stream.Collectors;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(BookMapper.class);


    @Mapping(target = "publisher", source = "publisher")
    @Mapping(target = "authors", source = "authors")
    BookJpaEntity fromBookEntityToBookJpaEntity(BookEntity bookEntity);

    @Mapping(target = "publisher", source = "publisher")
    @Mapping(target = "authors", source = "authors")
    BookEntity fromBookJpaEntityToBookEntity(BookJpaEntity bookJpaEntity);

    BookEntity fromBookRedisEntityToBookEntity(BookRedisEntity bookRedisEntity);

    BookRedisEntity fromBookEntityToBookRedisEntity(BookEntity bookEntity);
}
