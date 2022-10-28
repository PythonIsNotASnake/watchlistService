package com.pinas.watchlistService.controller;

import com.pinas.watchlistService.entity.Record;
import com.pinas.watchlistService.handler.RecordHandler;
import com.pinas.watchlistService.response.ResponseRecord;
import com.pinas.watchlistService.response.ResponseRecords;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("records")
public class RecordController {

    private final RecordHandler recordHandler;

    public RecordController(RecordHandler recordHandler) {
        this.recordHandler = recordHandler;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Record getRecord(@PathVariable("id") String id) {
        return recordHandler.getEntity(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseRecords getRecords(
            @RequestParam(name = "sortDirection", required = false) String sortDirection,
            @RequestParam(name = "sortValue", required = false) String sortValue,
            @RequestParam(name = "start", required = false) Long start,
            @RequestParam(name = "limit", required = false) Long limit
    ) {
        return recordHandler.getEntities(sortDirection, sortValue, start, limit);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseRecord createRecord(@RequestBody Record record) {
        return recordHandler.createEntity(record);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseRecord updateRecord(@PathVariable("id") Long id,
                                       @RequestBody Record record) {
        return recordHandler.updateEntity(id, record);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteRecord(@PathVariable("id") Long id) {
        return recordHandler.deleteEntity(id);
    }
}
