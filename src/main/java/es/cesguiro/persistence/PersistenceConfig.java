package es.cesguiro.persistence;

import es.cesguiro.persistence.dao.AuthorDao;
import es.cesguiro.persistence.dao.BookDao;
import es.cesguiro.persistence.dao.PublisherDao;
import es.cesguiro.persistence.dao.jpa.AuthorDaoJpa;
import es.cesguiro.persistence.dao.jpa.BookDaoJpa;
import es.cesguiro.persistence.dao.jpa.PublisherDaoJpa;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "es.cesguiro.persistence.dao.jpa")
@EntityScan(basePackages = "es.cesguiro.persistence.dao.jpa.entity")
public class PersistenceConfig {


    @Bean
    public PublisherDao publisherDao() {
        return new PublisherDaoJpa();
    }

    @Bean
    public BookDao bookDao() {
        return new BookDaoJpa();
    }

    @Bean
    public AuthorDao authorDao() {
        return new AuthorDaoJpa();
    }
}
