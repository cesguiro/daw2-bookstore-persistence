package es.cesguiro.data.mapper;

import es.cesguiro.domain.repository.entity.PublisherEntity;
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
}
