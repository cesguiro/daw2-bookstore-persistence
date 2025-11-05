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
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookRepositoryImplTest {

    @Mock
    private BookJpaDao bookJpaDao;

    @InjectMocks
    private BookRepositoryImpl bookRepositoryImpl;

    static Stream<Arguments> provideFindAllArguments() {
        return Stream.of(
                Arguments.of(1, 5, 2L, Instancio.ofList(InstancioModel.BOOK_JPA_ENTITY_MODEL).size(2).withSeed(10).create()),
                Arguments.of(1, 10, 2L, Instancio.ofList(InstancioModel.BOOK_JPA_ENTITY_MODEL).size(2).withSeed(20).create()),
                Arguments.of(1, 3, 3L, Instancio.ofList(InstancioModel.BOOK_JPA_ENTITY_MODEL).size(3).withSeed(30).create()),
                Arguments.of(1, 3, 9L, Instancio.ofList(InstancioModel.BOOK_JPA_ENTITY_MODEL).size(3).withSeed(40).create()),
                Arguments.of(2, 3, 5L, Instancio.ofList(InstancioModel.BOOK_JPA_ENTITY_MODEL).size(3).withSeed(50).create())
        );
    }

    @ParameterizedTest
    @DisplayName("findAll should return paginated BookEntity list")
    @MethodSource("provideFindAllArguments")
    void findAll_ShouldReturnPaginatedBookEntityList(int pageNumber, int pageSize, long expectedTotalElements, List<BookJpaEntity> bookJpaEntities) {
        when(bookJpaDao.findAll(anyInt(), anyInt()))
                .thenReturn(bookJpaEntities);
        when(bookJpaDao.count())
                .thenReturn(expectedTotalElements);

        List<BookEntity> bookEntities = bookJpaEntities.stream()
                .map(BookMapper.INSTANCE::fromBookJpaEntityToBookEntity)
                .toList();
        Page<BookEntity> expected = new Page<>(
                bookEntities,
                pageNumber,
                pageSize,
                expectedTotalElements
        );
        Page <BookEntity> result = bookRepositoryImpl.findAll(pageNumber, pageSize);

        // Assert
        assertAll(
                () -> assertNotNull(result, "Resulting Page should not be null"),
                () -> assertEquals(expected.totalElements(), result.totalElements(), "Total elements should match"),
                () -> assertEquals(expected.pageSize(), result.pageSize(), "Page size should match"),
                () -> assertEquals(expected.pageNumber(), result.pageNumber(), "Page number should match"),
                () -> assertEquals(expected.data().size(), result.data().size(), "Content size should match")
        );
    }


    @Test
    @DisplayName("findByIsbn should return BookEntity when found")
    void findByIsbn_ShouldReturnBookEntityWhenFound() {
        BookJpaEntity bookJpaEntity = Instancio.of(InstancioModel.BOOK_JPA_ENTITY_MODEL).create();

        when(bookJpaDao.findByIsbn(anyString()))
                .thenReturn(Optional.of(bookJpaEntity));

        Optional<BookEntity> expected = Optional.of(BookMapper.INSTANCE.fromBookJpaEntityToBookEntity(bookJpaEntity));
        Optional<BookEntity> result = bookRepositoryImpl.findByIsbn("some-isbn");


        // Assert
        assertAll(
                () -> assertTrue(result.isPresent(), "BookEntity should be present"),
                () -> assertEquals(expected.get().isbn(), result.get().isbn(), "ISBNs should match"),
                () -> assertEquals(expected.get().titleEs(), result.get().titleEs(), "TitleEs should match"),
                () -> assertEquals(expected.get().titleEn(), result.get().titleEn(), "TitleEn should match"),
                () -> assertEquals(expected.get().synopsisEs(), result.get().synopsisEs(), "SynopsisEs should match"),
                () -> assertEquals(expected.get().synopsisEn(), result.get().synopsisEn(), "SynopsisEn should match"),
                () -> assertEquals(expected.get().basePrice(), result.get().basePrice(), "BasePrices should match"),
                () -> assertEquals(expected.get().discountPercentage(), result.get().discountPercentage(), "DiscountPercentages should match"),
                () -> assertEquals(expected.get().cover(), result.get().cover(), "Covers should match"),
                () -> assertEquals(expected.get().publicationDate(), result.get().publicationDate(), "PublicationDates should match"),
                () -> assertEquals(expected.get().publisher().id(), result.get().publisher().id(), "Publisher IDs should match"),
                () -> assertEquals(expected.get().authors().size(), result.get().authors().size(), "Number of authors should match"),
                () -> assertEquals(expected.get().authors().getFirst().id(), result.get().authors().getFirst().id(), "First author IDs should match"),
                () -> assertEquals(expected.get().authors().getLast().id(), result.get().authors().getLast().id(), "Last author IDs should match")
        );
    }

    @Test
    @DisplayName("findByIsbn should return empty when not found")
    void findByIsbn_ShouldReturnEmptyWhenNotFound() {
        when(bookJpaDao.findByIsbn(anyString()))
                .thenReturn(Optional.empty());

        Optional<BookEntity> result = bookRepositoryImpl.findByIsbn("non-existent-isbn");

        // Assert
        assertFalse(result.isPresent(), "BookEntity should not be present");
    }

}