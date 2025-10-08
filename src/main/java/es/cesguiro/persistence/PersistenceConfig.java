package es.cesguiro.persistence;

import es.cesguiro.persistence.dao.jpa.AuthorJpaDao;
import es.cesguiro.persistence.dao.jpa.BookJpaDao;
import es.cesguiro.persistence.dao.jpa.PublisherJpaDao;
import es.cesguiro.persistence.dao.jpa.impl.AuthorJpaDaoJpaImpl;
import es.cesguiro.persistence.dao.jpa.impl.BookJpaDaoJpaImpl;
import es.cesguiro.persistence.dao.jpa.impl.PublisherJpaDaoJpaImpl;
import es.cesguiro.persistence.dao.redis.BookRedisDao;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
//@EnableJpaRepositories(basePackages = "es.cesguiro.persistence.dao.jpa")
@EnableRedisRepositories(basePackages = "es.cesguiro.persistence.dao.redis")
@EntityScan(basePackages = "es.cesguiro.persistence.dao.jpa.entity")
public class PersistenceConfig {


    @Bean
    public PublisherJpaDao publisherJpaDao() {
        return new PublisherJpaDaoJpaImpl();
    }

    @Bean
    public BookJpaDao bookJpaDao() {
        return new BookJpaDaoJpaImpl();
    }

    @Bean
    public AuthorJpaDao authorJpaDao() {
        return new AuthorJpaDaoJpaImpl();
    }

}
