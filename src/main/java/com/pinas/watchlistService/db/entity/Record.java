package com.pinas.watchlistService.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Locale;

@Data
@Entity
@Table
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String genre;

    @Column
    private String link;

}
