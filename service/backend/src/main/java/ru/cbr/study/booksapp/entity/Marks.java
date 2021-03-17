package ru.cbr.study.booksapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "marks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Marks {

    @Id
    private int book_id;

    @Column(name = "likes")
    private int likes;

    @Column(name = "dislikes")
    private int dislikes;

    @OneToOne
    @JoinColumn(name = "book_id")
    @MapsId
    Book book;

}
