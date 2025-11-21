package es.cesguiro.persistence.dao.jpa.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

public abstract class BaseJpaDaoTest<T> {

    @Autowired
    protected T dao;

    // Singleton MariaDBContainer for all tests
    static final MariaDBContainer<?> mariaDBContainer;

    /*@Container
    static MariaDBContainer<?> mariaDBContainer = new  MariaDBContainer<>(
            DockerImageName.parse("mariadb:10.11")
    );*/

    static {
        mariaDBContainer = new MariaDBContainer<>(
                DockerImageName.parse("mariadb:10.11")
        );
        mariaDBContainer.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mariaDBContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mariaDBContainer::getUsername);
        registry.add("spring.datasource.password", mariaDBContainer::getPassword);
    }


    public BaseJpaDaoTest() {

    }

    /*@BeforeEach
    @DataSet(cleanBefore = true, skipCleaningFor = {})
    void cleanDatabase() {
        // Database is cleaned before each test
    }

    // Método auxiliar para forzar el commit de la transacción en pruebas y permitir que DBRider compare los datos
    protected void flushAndCommitForDbRider() {
        // **Forzamos el commit ANTES de que el método termine y DBRider compare**
        TestTransaction.flagForCommit(); // Marca la transacción actual para commit
        TestTransaction.end(); // Finaliza la transacción actual (ejecuta el commit)
        TestTransaction.start(); // Inicia una nueva transacción (para el post-procesamiento si es necesario)
    }*/
}
