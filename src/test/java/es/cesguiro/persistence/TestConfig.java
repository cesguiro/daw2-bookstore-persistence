package es.cesguiro.persistence;

import es.cesguiro.persistence.dao.jpa.AuthorJpaDao;
import es.cesguiro.persistence.dao.jpa.BookJpaDao;
import es.cesguiro.persistence.dao.jpa.PublisherJpaDao;
import es.cesguiro.persistence.dao.jpa.impl.AuthorJpaDaoImpl;
import es.cesguiro.persistence.dao.jpa.impl.BookJpaDaoImpl;
import es.cesguiro.persistence.dao.jpa.impl.PublisherJpaDaoImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

//@Configuration
@TestConfiguration
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
    public PublisherJpaDao publisherJpaDao() {
        return new PublisherJpaDaoImpl();
    }

    @Bean
    public BookJpaDao bookJpaDao() {
        return new BookJpaDaoImpl();
    }

    @Bean
    public AuthorJpaDao authorJpaDao() {
        return new AuthorJpaDaoImpl();
    }

    /*@Bean
    public MariaDBContainer mariaDBContainer() {
        return new MariaDBContainer(DockerImageName.parse("mariadb:10.11"));
    }*/


}
