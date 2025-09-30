package es.cesguiro.data.loader;

import org.apache.commons.csv.CSVRecord;

import java.util.List;

public class BooksAuthorsDataLoader extends ResourceDataLoader{

    private List<CSVRecord> booksAuthorsRawRecords;

    public BooksAuthorsDataLoader() {
        super("books_authors.csv");
        booksAuthorsRawRecords = loadDataFromCsv();
    }

    public Long[] getAllAuthorIdsByBookId(Long id) {
        return booksAuthorsRawRecords.stream()
                .map(record -> {
                    long bookId = Long.parseLong(record.get("book_id"));
                    long authorId = Long.parseLong(record.get("author_id"));
                    return new long[]{bookId, authorId};
                })
                .filter(ids -> ids[0] == id)
                .map(ids -> ids[1])
                .toArray(Long[]::new);
    }
}
