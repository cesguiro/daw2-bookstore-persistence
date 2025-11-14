package es.cesguiro.persistence.repository;

import org.mockito.InjectMocks;
import org.mockito.Mock;

public abstract class BaseRepositoryTest<T, R> {

    @Mock
    protected R dao;

    @InjectMocks
    protected T repository;
}
