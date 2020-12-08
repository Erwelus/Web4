package com.example.web4.model;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
public abstract class SimpleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
