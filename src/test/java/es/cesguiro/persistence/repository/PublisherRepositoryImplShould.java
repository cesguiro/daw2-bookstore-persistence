package es.cesguiro.persistence.repository;

import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.persistence.dao.jpa.PublisherJpaDao;
import es.cesguiro.persistence.dao.jpa.entity.PublisherJpaEntity;
import es.cesguiro.persistence.util.InstancioModel;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublisherRepositoryImplShould{

    @Mock
    private PublisherJpaDao publisherJpaDao;

    @InjectMocks
    private PublisherRepositoryImpl publisherRepository;

    @Test
    @DisplayName("Test findBySlug method returns Optional<PublisherEntity>")
    void return_publisher_when_slug_exists() {
        PublisherJpaEntity publisherJpaEntity = Instancio.of(InstancioModel.PUBLISHER_JPA_ENTITY_MODEL)
                .withSeed(10)
                .create();
        PublisherEntity expected = Instancio.of(InstancioModel.PUBLISHER_ENTITY_MODEL)
                .withSeed(10)
                .create();
        when(publisherJpaDao.findBySlug(any())).thenReturn(Optional.of(publisherJpaEntity));

        Optional<PublisherEntity> result = publisherRepository.findBySlug("some-slug");
        assertThat(result)
                .hasValueSatisfying(publisher -> assertThat(publisher)
                        .extracting(PublisherEntity::name, PublisherEntity::slug)
                        .containsExactly(expected.name(), expected.slug())
                );
    }

    @Test
    void return_empty_when_slug_does_not_exits() {
        when(publisherJpaDao.findBySlug(any())).thenReturn(Optional.empty());
        assertThat(publisherRepository.findBySlug("non-existing-slug")).isNotPresent();
    }

    @Test
    void return_list_of_publishers() {
         List<PublisherJpaEntity> jpaEntities = Instancio.ofList(InstancioModel.PUBLISHER_JPA_ENTITY_MODEL)
                .size(2)
                .withSeed(20)
                .create();
         List<PublisherEntity> expected = Instancio.ofList(InstancioModel.PUBLISHER_ENTITY_MODEL)
                .size(2)
                .withSeed(20)
                .create();
        when(publisherJpaDao.findAll(0, 10)).thenReturn(jpaEntities);

        List<PublisherEntity> result = publisherRepository.findAll(0, 10);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }
}