package ru.cbr.study.book.dto;

import lombok.Data;

@Data
public class AuthorDto extends BaseDto{
    private String name;
    private String surname;

    public String getNameAndSurname(){
        return name + " " + surname;
    }
}
