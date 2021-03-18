package ru.cbr.study.booksapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book extends ru.cbr.study.booksapp.entity.Entity {

    @Column(name = "book_name")
    private String bookName;
    @Column(name = "annotation")
    private String annotation;
    @Column(name = "year")
    private int year;

    @OneToMany(mappedBy = "book", cascade=CascadeType.ALL)
    private List<Comment> comments;

    @Column(name = "authorId")
    private int authorId;

    @ManyToOne
    @JoinColumn(name = "authorId", insertable=false, updatable=false)
    private Author author;

    @OneToOne(mappedBy = "book", cascade=CascadeType.ALL)
    Marks marks;
}
