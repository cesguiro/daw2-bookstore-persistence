package es.cesguiro.persistence.annotation;


import es.cesguiro.persistence.TestConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
//@DataJpaTest(showSql = false)
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@Transactional
//@DBRider
@EnableSqlLogging
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
//@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureTestDatabase(replace =  AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public @interface DaoTest {
}
