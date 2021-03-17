package ru.cbr.study.book.dto;

import lombok.Data;

@Data
public class BookDto extends BaseDto {
    private String bookName;
    private String annotation;
    private int year;
    private int authorId;

    //public String getAuthor(){
//        return authorDto.getName() + " " +authorDto.getSurname();
//    }
}
