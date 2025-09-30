package es.cesguiro.data.loader;

import es.cesguiro.data.mapper.AuthorMapper;
import es.cesguiro.domain.model.Author;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.service.dto.AuthorDto;
import org.apache.commons.csv.CSVRecord;

import java.util.List;

public class AuthorsDataLoader extends ResourceDataLoader {

    private final List<CSVRecord> authorRawRecords;

    public AuthorsDataLoader() {
        super("authors.csv");
        this.authorRawRecords = loadDataFromCsv();
    }


    public List<AuthorEntity> loadAuthorEntitiesFromCSV() {
        return authorRawRecords
                .stream()
                .map(AuthorMapper::toAuthorEntity)
                .toList();
    }

}
