package es.cesguiro.persistence.dao.jpa.mapper;

import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.persistence.dao.jpa.entity.BookAuthorJpaEntity;
import es.cesguiro.persistence.dao.jpa.entity.BookJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(BookMapper.class);

    @Mapping(target = "publisher", source = "publisher")
    @Mapping(target = "authors", source = "authors")
    BookJpaEntity fromBookEntityToBookJpaEntity(BookEntity bookEntity);

    @Mapping(target = "publisher", source = "publisher")
    @Mapping(target = "authors", source = "authors")
    BookEntity fromBookJpaEntityToBookEntity(BookJpaEntity bookJpaEntity);


}
