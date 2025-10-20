package es.cesguiro.data.mapper;

import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.persistence.dao.jpa.entity.PublisherJpaEntity;
import org.apache.commons.csv.CSVRecord;

public class PublisherMapper extends BaseMapper {

    public static PublisherEntity toPublisherEntity(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        return new PublisherEntity(
                parseLong(csvRecord.get("id")),
                parseString(csvRecord.get("name")),
                parseString(csvRecord.get("slug"))
        );
    }

    public static PublisherJpaEntity toPublisherJpaEntity(CSVRecord csvRecord) {
        if (csvRecord == null) {
            return null;
        }
        return new PublisherJpaEntity(
                parseLong(csvRecord.get("id")),
                parseString(csvRecord.get("name")),
                parseString(csvRecord.get("slug"))
        );
    }
}
