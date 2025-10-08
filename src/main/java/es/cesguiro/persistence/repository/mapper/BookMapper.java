package es.cesguiro.persistence.repository.mapper;

import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.persistence.dao.jpa.entity.BookJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
