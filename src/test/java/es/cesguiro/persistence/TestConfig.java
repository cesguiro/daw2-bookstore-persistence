package es.cesguiro.persistence;

import es.cesguiro.persistence.dao.jpa.AuthorJpaDao;
import es.cesguiro.persistence.dao.jpa.BookJpaDao;
import es.cesguiro.persistence.dao.jpa.PublisherJpaDao;
import es.cesguiro.persistence.dao.jpa.impl.AuthorJpaDaoImpl;
import es.cesguiro.persistence.dao.jpa.impl.BookJpaDaoImpl;
import es.cesguiro.persistence.dao.jpa.impl.PublisherJpaDaoImpl;
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
    public PublisherJpaDao publisherJpaDao(EntityManager entityManager) {
        return new PublisherJpaDaoImpl();
    }

    @Bean
    public BookJpaDao bookJpaDao(EntityManager entityManager) {
        return new BookJpaDaoImpl();
    }

    @Bean
    public AuthorJpaDao authorJpaDao(EntityManager entityManager) {
        return new AuthorJpaDaoImpl();
    }


}
