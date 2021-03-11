package ru.cbr.study.book.dto;

import lombok.Data;

@Data
public class CommentDto extends BaseDto {
    private String userName;
    private String comment;
    private BookDto bookDto;
}
