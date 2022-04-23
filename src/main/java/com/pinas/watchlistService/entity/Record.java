package com.pinas.watchlistService.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Locale;

@Data
@Document
public class Record {

    public enum SortableRecordValue {
        ID, TITLE, DESCRIPTION, GENRE, LINK;

        public static SortableRecordValue fromString(String value) {
            try {
                return SortableRecordValue.valueOf(value.toUpperCase(Locale.US));
            } catch (Exception e) {
                throw new IllegalArgumentException(String.format(
                        "Invalid value '%s' for sortable value given.", value), e);
            }
        }
    }

    @Id
    private String id;
    private String title;
    private String description;
    private String genre;
    private String link;

}
