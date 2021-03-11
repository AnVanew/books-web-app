package ru.cbr.study.booksapp.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class Entity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
}
