package com.pinas.watchlistService.api.response;

import com.pinas.watchlistService.db.entity.Record;
import lombok.Data;

@Data
public class ResponseRecord {
    private Record data;
}
