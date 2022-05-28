package com.pinas.watchlistService.response;

import com.pinas.watchlistService.entity.Record;
import lombok.Data;

import java.util.List;

@Data
public class ResponseRecords {
    private List<Record> data;
    private long count;
    private long total;
}
