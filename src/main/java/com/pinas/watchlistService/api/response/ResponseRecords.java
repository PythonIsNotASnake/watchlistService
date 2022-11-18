package com.pinas.watchlistService.api.response;

import com.pinas.watchlistService.db.entity.Record;
import lombok.Data;

import java.util.List;

@Data
public class ResponseRecords {
    private List<Record> data;
    private long count;
    private long total;
}
