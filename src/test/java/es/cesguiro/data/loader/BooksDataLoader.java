package es.cesguiro.data.loader;

import es.cesguiro.data.mapper.BookMapper;
import es.cesguiro.domain.model.Book;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.service.dto.BookDto;
import es.cesguiro.persistence.dao.jpa.entity.BookJpaEntity;
import org.apache.commons.csv.CSVRecord;

import java.util.List;

public class BooksDataLoader extends ResourceDataLoader {

    private final List<CSVRecord> bookRawRecords;

    public BooksDataLoader() {
        super("books.csv");
        bookRawRecords = loadDataFromCsv();
    }


    public List<BookEntity> loadBookEntitiesFromCSV() {
        return bookRawRecords
                .stream()
                .map(BookMapper::toBookEntity)
                .toList();
    }

    public List<BookJpaEntity> loadBookJpaEntitiesFromCSV() {
        return bookRawRecords
                .stream()
                .map(BookMapper::toBookJpaEntity)
                .toList();
    }

}
