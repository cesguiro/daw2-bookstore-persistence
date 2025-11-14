package es.cesguiro.persistence.repository;

import es.cesguiro.domain.model.Page;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.persistence.dao.jpa.BookJpaDao;
import es.cesguiro.persistence.dao.jpa.entity.BookJpaEntity;
import es.cesguiro.persistence.repository.mapper.BookMapper;
import es.cesguiro.persistence.util.InstancioModel;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookRepositoryImplShould {

    @Mock
    private BookJpaDao bookJpaDao;

    @InjectMocks
    private BookRepositoryImpl bookRepository;


    @ParameterizedTest
    @CsvSource({
            "1, 2, 2, 5",
            "1, 5, 5, 5",
            "1, 5, 3, 3",
            "2, 4, 4, 10",
            "2, 4, 4, 4",
            "2, 4, 3, 3"
    })
    public void return_page_of_bookEntity(int page, int size, int elementsToCreate, long totalElements){
        List<BookJpaEntity> bookJpaEntities = Instancio.ofList(InstancioModel.BOOK_JPA_ENTITY_MODEL)
                .size(elementsToCreate)
                .withSeed(20)
                .create();
        List<BookEntity> expectedBookEntities = bookJpaEntities.stream()
                .map(BookMapper.INSTANCE::fromBookJpaEntityToBookEntity)
                .toList();
        when(bookJpaDao.findAll(anyInt(), anyInt())).thenReturn(bookJpaEntities);
        when(bookJpaDao.count()).thenReturn(totalElements);

        Page<BookEntity> expected = new Page<>(expectedBookEntities, page, size, totalElements);
        Page<BookEntity> result = bookRepository.findAll(page, size);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }



    @Test
    @DisplayName("findByIsbn should return BookEntity when found")
    void return_optional_bookEntity_when_isbn_exists() {
        BookJpaEntity bookJpaEntity = Instancio.of(InstancioModel.BOOK_JPA_ENTITY_MODEL).create();

        when(bookJpaDao.findByIsbn(anyString()))
                .thenReturn(Optional.of(bookJpaEntity));

        Optional<BookEntity> expected = Optional.of(BookMapper.INSTANCE.fromBookJpaEntityToBookEntity(bookJpaEntity));
        Optional<BookEntity> result = bookRepository.findByIsbn("some-isbn");

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("findByIsbn should return empty when not found")
    void return_optional_empty_when_isbn_does_not_exist() {
        when(bookJpaDao.findByIsbn(anyString()))
                .thenReturn(Optional.empty());

        Optional<BookEntity> result = bookRepository.findByIsbn("non-existent-isbn");

        assertThat(result).isEmpty();
    }

}