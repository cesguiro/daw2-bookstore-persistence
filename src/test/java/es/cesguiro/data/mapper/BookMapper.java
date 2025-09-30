package es.cesguiro.data.mapper;

import es.cesguiro.data.loader.AuthorsDataLoader;
import es.cesguiro.data.loader.BooksAuthorsDataLoader;
import es.cesguiro.data.loader.PublishersDataLoader;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookMapper extends BaseMapper{

    private static final PublishersDataLoader publishersDataLoader = new PublishersDataLoader();
    private static final AuthorsDataLoader authorsDataLoader = new AuthorsDataLoader();

    public static BookEntity toBookEntity(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        PublisherEntity publisher = PublisherMapper.toPublisherEntity(getPublisherCsvRecord(parseLong(csvRecord.get("publisher_id"))));
        List<CSVRecord> authorCsvRecords = getAuthorCsvRecords(parseLong(csvRecord.get("id")));
        List<AuthorEntity> authors;
        if(authorCsvRecords == null) {
            authors = null;
        } else {
            authors = authorCsvRecords.stream().map(AuthorMapper::toAuthorEntity).collect(Collectors.toCollection(ArrayList::new));;
        }
        return new BookEntity(
                parseLong(csvRecord.get("id")),
                parseString(csvRecord.get("isbn")),
                parseString(csvRecord.get("title_es")),
                parseString(csvRecord.get("title_en")),
                parseString(csvRecord.get("synopsis_es")),
                parseString(csvRecord.get("synopsis_en")),
                parseBigDecimal(csvRecord.get("base_price")),
                parseDouble(csvRecord.get("discount_percentage")),
                parseString(csvRecord.get("cover")),
                parseDate(csvRecord.get("publication_date")),
                publisher,
                authors
        );
    }


    private static CSVRecord getPublisherCsvRecord(Long id) {
        if (id == null) {
            return null;
        }
        return publishersDataLoader.findCsvRecordById(id).orElse(null);
    }

    private static List<CSVRecord> getAuthorCsvRecords(Long id) {
        BooksAuthorsDataLoader booksAuthorsDataLoader = new BooksAuthorsDataLoader();
        Long[] authorIds = booksAuthorsDataLoader.getAllAuthorIdsByBookId(id);
        if (authorIds.length == 0) {
            return null;
        }
        return authorsDataLoader.findAllCsvRecordsByIds(authorIds);
    }

}
