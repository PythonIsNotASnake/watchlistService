package com.pinas.watchlistService.handler;

import com.pinas.watchlistService.entity.Record;
import com.pinas.watchlistService.repository.RecordRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RecordHandler {

    private final RecordRepository repository;

    public RecordHandler(RecordRepository repository) {
        this.repository = repository;
    }

    public Record getEntity(String id) {
        Optional<Record> entity = repository.findById(id);
        return entity.orElse(null);
    }

    public List<Record> getEntities(
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

        return repository.findAll(request).getContent();
    }

    public Record createEntity(Record entity) {
        return repository.insert(entity);
    }

    public Record updateEntity(String id, Record entity) {
        entity.setId(id);
        return repository.save(entity);
    }

    public String deleteEntity(String id) {
        try {
            repository.deleteById(id);
            return "Successfully deleted.";
        } catch (Exception e) {
            return "Record could not be deleted: " + e.getMessage();
        }
    }
}
