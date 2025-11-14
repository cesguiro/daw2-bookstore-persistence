package es.cesguiro.persistence.dao.jpa.impl;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import es.cesguiro.persistence.annotation.DaoTest;
import es.cesguiro.persistence.dao.jpa.AuthorJpaDao;
import es.cesguiro.persistence.dao.jpa.entity.AuthorJpaEntity;
import org.junit.jupiter.api.Test;

@DaoTest
class AuthorJpaDaoImplShould extends BaseJpaDaoTest<AuthorJpaDao> {


    @Test
    @DataSet(value= "adapters/data/authors.json")
    @ExpectedDataSet(value= "adapters/data/authors-after-insert.json", ignoreCols = {"id"})
    void insert_author_correctly() {
        AuthorJpaEntity newAuthor = new AuthorJpaEntity(
                null,
                "Mark Twain",
                "American",
                "Mark Twain fue un escritor y humorista estadounidense, conocido por sus novelas 'Las aventuras de Tom Sawyer' y 'Las aventuras de Huckleberry Finn'.",
                "Mark Twain was an American writer and humorist, known for his novels 'The Adventures of Tom Sawyer' and 'The Adventures of Huckleberry Finn'.",
                1835,
                1910,
                "mark-twain"
        );
        dao.insert(newAuthor);
        flushAndCommitForDbRider();
    }

}