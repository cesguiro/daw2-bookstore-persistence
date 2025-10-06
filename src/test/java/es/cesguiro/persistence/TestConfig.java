package es.cesguiro.persistence;

import es.cesguiro.persistence.dao.AuthorDao;
import es.cesguiro.persistence.dao.BookDao;
import es.cesguiro.persistence.dao.PublisherDao;
import es.cesguiro.persistence.dao.jpa.AuthorDaoJpa;
import es.cesguiro.persistence.dao.jpa.BookDaoJpa;
import es.cesguiro.persistence.dao.jpa.PublisherDaoJpa;
import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "es.cesguiro.persistence.dao.jpa")
@EntityScan(basePackages = "es.cesguiro.persistence.dao.jpa.entity")
public class TestConfig {

    // Spring inyecta automáticamente el ApplicationContext
    /*private final ApplicationContext applicationContext;

    public TestConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public Flyway flyway() {
        return Flyway.configure()
                .dataSource(applicationContext.getBean(DataSource.class))
                .locations("classpath:db/migration")
                .cleanDisabled(false)
                .load();
    }*/

    /*@Bean
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .cleanDisabled(false)
                .load();
    }*/

    @Bean
    public PublisherDao publisherDao(EntityManager entityManager) {
        return new PublisherDaoJpa();
    }

    @Bean
    public BookDao bookDao(EntityManager entityManager) {
        return new BookDaoJpa();
    }

    @Bean
    public AuthorDao authorDao(EntityManager entityManager) {
        return new AuthorDaoJpa();
    }


}
