package ru.cbr.study.booksapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends ru.cbr.study.booksapp.entity.Entity {

    @Column(name = "user_name")
    private String userName;
    @Column(name = "comment")
    private String comment;

    @Column(name = "book_id")
    private int bookId;

    @ManyToOne
    @JoinColumn(name = "book_id", insertable=false, updatable=false)
    private Book book;

}
