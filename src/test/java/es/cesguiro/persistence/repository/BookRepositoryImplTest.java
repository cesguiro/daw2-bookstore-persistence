package es.cesguiro.persistence.repository;

import es.cesguiro.persistence.dao.jpa.BookJpaDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class BookRepositoryImplTest {

    @Mock
    private BookJpaDao bookJpaDao;

    @InjectMocks
    private BookRepositoryImpl bookRepositoryImpl;

    @Test
    void testBookMapping() {
        System.out.println("Testing BookEntity to Book mapping...");
    }

}