package es.cesguiro.data.loader;

import es.cesguiro.data.mapper.PublisherMapper;
import es.cesguiro.domain.model.Publisher;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.domain.service.dto.PublisherDto;
import org.apache.commons.csv.CSVRecord;

import java.util.List;

public class PublishersDataLoader extends ResourceDataLoader {

    private final List<CSVRecord> publisherRawRecords;

    public PublishersDataLoader() {
        super("publishers.csv");
        publisherRawRecords = loadDataFromCsv();
    }

    public List<PublisherEntity> loadPublisherEntitiesFromCSV() {
        return publisherRawRecords
                .stream()
                .map(PublisherMapper::toPublisherEntity)
                .toList();
    }

}
