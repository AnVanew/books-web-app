package ru.cbr.study.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarksDto {

    private int bookId;
    private int likes;
    private int dislikes;
}
