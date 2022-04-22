package com.pinas.watchlistService.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Record {

    @Id
    private String id;
    private String title;
    private String description;
    private String genre;
    private String link;

}
