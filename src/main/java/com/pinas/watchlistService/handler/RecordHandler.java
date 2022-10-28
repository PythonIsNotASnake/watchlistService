package com.pinas.watchlistService.handler;

import com.dropbox.core.DbxException;
import com.pinas.watchlistService.DropboxConfig;
import com.pinas.watchlistService.entity.Record;
import com.pinas.watchlistService.repository.RecordRepository;
import com.pinas.watchlistService.response.ResponseRecord;
import com.pinas.watchlistService.response.ResponseRecords;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RecordHandler {

    private final RecordRepository repository;
    private final DropboxConfig dropboxConfig;

    public RecordHandler(RecordRepository repository, DropboxConfig dropboxConfig) {
        this.repository = repository;
        this.dropboxConfig = dropboxConfig;
    }

    public Record getEntity(String id) {
        Optional<Record> entity = repository.findById(id);
        return entity.orElse(null);
    }

    public ResponseRecords getEntities(
            String sortDirection,
            String sortValue,
            Long start,
            Long limit
    ) {
        if (start == null)
            start = 0L;
        if (limit == null)
            limit = 50L;
        else if (limit > 250L)
            limit = 250L;

        Sort.Direction direction = null;
        try {
            direction = Sort.Direction.fromString(sortDirection);
        } catch (Exception e) {
            System.out.println("Invalid sort direction. Use default ASC");
            direction = Sort.Direction.ASC;
        }

        Record.SortableRecordValue sortableValue = null;
        try {
            sortableValue = Record.SortableRecordValue.fromString(sortValue);
        } catch (Exception e) {
            System.out.println("Invalid sort value. Use default value 'id'");
            sortableValue = Record.SortableRecordValue.ID;
        }

        PageRequest request = PageRequest.of(
                start.intValue(),
                limit.intValue(),
                Sort.by(direction, sortableValue.name().toLowerCase()));

        List<Record> records = repository.findAll(request).getContent();
        long total = repository.count();

        return buildResponseRecords(records, total);
    }

    public ResponseRecord createEntity(Record entity) {
        validateEntity(entity);
        Record save = repository.save(entity);
        backupRecords();
        return buildResponseRecord(save);
    }

    public ResponseRecord updateEntity(Long id, Record entity) {
        entity.setId(id);
        validateEntity(entity);
        Record save = repository.save(entity);
        backupRecords();
        return buildResponseRecord(save);
    }

    public String deleteEntity(Long id) {
        try {
            repository.deleteById(String.valueOf(id));
            backupRecords();
            return "Successfully deleted.";
        } catch (Exception e) {
            return "Record could not be deleted: " + e.getMessage();
        }
    }

    private ResponseRecord buildResponseRecord(Record record) {
        ResponseRecord response = new ResponseRecord();
        response.setData(record);
        return response;
    }

    private ResponseRecords buildResponseRecords(List<Record> records, long total) {
        ResponseRecords response = new ResponseRecords();
        response.setData(records);
        response.setCount(records.size());
        response.setTotal(total);
        return response;
    }

    private void validateEntity(Record record) {
        if (record.getLink() != null && !record.getLink().startsWith("http://www.youtube.com/embed/"))
            throw new InvalidPropertyException(Record.class, "link", "Link must be start with 'http://www.youtube.com/embed/'");
    }

    private void backupRecords() {
        List<Record> allRecords = repository.findAll();
        dropboxConfig.uploadBackup(allRecords);
    }
}
