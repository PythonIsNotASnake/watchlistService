package com.pinas.watchlistService.db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
public class Authorization {

    @Id
    private String key;

    @Column
    private String value;

}
