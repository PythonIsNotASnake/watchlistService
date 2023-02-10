package com.pinas.watchlistService.handler;

import com.pinas.watchlistService.api.response.ResponseRecord;
import com.pinas.watchlistService.api.response.ResponseRecords;
import com.pinas.watchlistService.db.entity.Record;
import com.pinas.watchlistService.db.repository.RecordRepository;
import com.pinas.watchlistService.helper.AccessTokenHelper;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RecordHandler {

    private final RecordRepository repository;

    public RecordHandler(RecordRepository repository, AccessTokenHelper accessTokenHelper) {
        this.repository = repository;
    }

    public Record getEntity(Long id) {
        Optional<Record> entity = repository.findById(id);
        return entity.orElse(null);
    }

    public ResponseRecords getEntities(
            String sortDirection,
            String sortValue,
            Long start,
            Long limit,
            String filterTitle,
            String filterGenre) {

        PageRequest request = buildPageRequest(sortDirection, sortValue, start, limit);

        Specification<Record> specification = buildFilterSpecification(filterTitle, filterGenre);

        Page<Record> recordPage = null;
        if (specification != null) {
            recordPage = repository.findAll(specification, request);
        } else {
            recordPage = repository.findAll(request);
        }
        List<Record> records = recordPage.getContent();
        long total = recordPage.getTotalElements();

        return buildResponseRecords(records, total);
    }

    private Specification<Record> buildFilterSpecification(String filterTitle, String filterGenre) {
        Specification<Record> specification = null;
        if (filterTitle != null && !filterTitle.isEmpty()) {
            Specification<Record> titleSpecification = (root, cq, cb) ->
                    cb.like(cb.lower(root.get("title")), "%" + filterTitle.toLowerCase() + "%");
            specification = specification != null ? specification.and(titleSpecification) : titleSpecification;
        }
        if (filterGenre != null && !filterGenre.isEmpty()) {
            Specification<Record> genreSpecification = (root, cq, cb) ->
                    cb.like(cb.lower(root.get("genre")), "%" + filterGenre.toLowerCase() + "%");
            specification = specification != null ? specification.and(genreSpecification) : genreSpecification;
        }
        return specification;
    }

    private PageRequest buildPageRequest(
            String sortDirection,
            String sortValue,
            Long start,
            Long limit) {

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

        return PageRequest.of(
                start.intValue(),
                limit.intValue(),
                Sort.by(direction, sortableValue.name().toLowerCase()));
    }

    public ResponseRecord createEntity(Record entity) {
        validateEntity(entity);
        Record save = repository.save(entity);
        return buildResponseRecord(save);
    }

    public ResponseRecord updateEntity(Long id, Record entity) {
        entity.setId(id);
        validateEntity(entity);
        Record save = repository.save(entity);
        return buildResponseRecord(save);
    }

    public String deleteEntity(Long id) {
        try {
            repository.deleteById(id);
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
        if (record.getLink() != null && !record.getLink().startsWith("https://www.youtube.com/embed/"))
            throw new InvalidPropertyException(Record.class, "link", "Link must be start with 'https://www.youtube.com/embed/'");
    }
}
