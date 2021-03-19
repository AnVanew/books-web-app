package ru.cbr.study.book.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class BookDto extends BaseDto {
    private String bookName;
    private String annotation;
    private int year;
    private int authorId;


}
