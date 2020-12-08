package com.example.web4.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "results")
public class Result extends SimpleEntity{
    private Double x;
    private Double y;
    private Double r;
    private Boolean hit;

    @ManyToOne
    @JoinColumn(referencedColumnName = "Id")
    private User owner;
}
