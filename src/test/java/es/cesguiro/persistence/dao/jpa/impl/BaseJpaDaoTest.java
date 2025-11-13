package es.cesguiro.persistence.dao.jpa.impl;

import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TestTransaction;

public abstract class BaseJpaDaoTest<T> {

    @Autowired
    protected T dao;

    @BeforeEach
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
    }
}
