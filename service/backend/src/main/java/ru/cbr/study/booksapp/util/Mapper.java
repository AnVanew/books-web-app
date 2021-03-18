package ru.cbr.study.booksapp.util;

import ru.cbr.study.book.dto.AuthorDto;
import ru.cbr.study.book.dto.BookDto;
import ru.cbr.study.book.dto.CommentDto;
import ru.cbr.study.book.dto.MarksDto;
import ru.cbr.study.booksapp.entity.Author;
import ru.cbr.study.booksapp.entity.Book;
import ru.cbr.study.booksapp.entity.Comment;
import ru.cbr.study.booksapp.entity.Marks;

public class Mapper {

    public static AuthorDto toDto(Author author){
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        authorDto.setSurname(author.getSurname());
        return authorDto;
    }

    public static Author toEntity(AuthorDto authorDto){
        Author author = new Author();
        author.setName(authorDto.getName());
        author.setSurname(authorDto.getSurname());
        if(authorDto.getId()!=0)author.setId(authorDto.getId());
        return author;
    }

    public static BookDto toDto(Book book){
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setBookName(book.getBookName());
        bookDto.setYear(book.getYear());
        bookDto.setAnnotation(book.getAnnotation());
        bookDto.setAuthorId(book.getAuthor().getId());
        return bookDto;
    }

    public static Book toEntity(BookDto bookDto){
        Book book = new Book();
        if(bookDto.getId()!=0)book.setId(bookDto.getId());
        book.setBookName(bookDto.getBookName());
        book.setAnnotation(bookDto.getAnnotation());
        book.setYear(bookDto.getYear());
        book.setAuthorId(bookDto.getAuthorId());
        return book;
    }

    public static CommentDto toDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setComment(comment.getComment());
        commentDto.setUserName(comment.getUserName());
        commentDto.setBookDto(Mapper.toDto(comment.getBook()));
        return commentDto;
    }

    public static Comment toEntity(CommentDto commentDto){
        Comment comment = new Comment();
        if(commentDto.getId()!=0)comment.setId(commentDto.getId());
        comment.setComment(commentDto.getComment());
        comment.setUserName(commentDto.getUserName());
        comment.setBook(Mapper.toEntity(commentDto.getBookDto()));
        return comment;
    }

    public static Marks toEntity(MarksDto marksDto){
        Marks marks = new Marks();
        marks.setBook(Mapper.toEntity(marksDto.getBookDto()));
        marks.setLikes(marksDto.getLikes());
        marks.setDislikes(marksDto.getDislikes());
        return marks;
    }

    public static MarksDto toDto(Marks marks){
        MarksDto marksDto = new MarksDto();
        marksDto.setBookDto(Mapper.toDto(marks.getBook()));
        marksDto.setLikes(marks.getLikes());
        marksDto.setDislikes(marks.getDislikes());
        return marksDto;
    }
}
