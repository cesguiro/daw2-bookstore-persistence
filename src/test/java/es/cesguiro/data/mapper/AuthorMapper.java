package es.cesguiro.data.mapper;

import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.persistence.dao.jpa.entity.AuthorJpaEntity;
import org.apache.commons.csv.CSVRecord;

public class AuthorMapper extends BaseMapper {

    public static AuthorEntity toAuthorEntity(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        return new AuthorEntity(
                parseLong(csvRecord.get("id")),
                parseString(csvRecord.get("name")),
                parseString(csvRecord.get("nationality")),
                parseString(csvRecord.get("biography_es")),
                parseString(csvRecord.get("biography_en")),
                parseInt(csvRecord.get("birth_year")),
                parseInt(csvRecord.get("death_year")),
                parseString(csvRecord.get("slug"))
        );
    }


    public static AuthorJpaEntity toAuthorJpaEntity(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        return new AuthorJpaEntity(
                parseLong(csvRecord.get("id")),
                parseString(csvRecord.get("name")),
                parseString(csvRecord.get("nationality")),
                parseString(csvRecord.get("biography_es")),
                parseString(csvRecord.get("biography_en")),
                parseInt(csvRecord.get("birth_year")),
                parseInt(csvRecord.get("death_year")),
                parseString(csvRecord.get("slug"))
        );
    }
}
