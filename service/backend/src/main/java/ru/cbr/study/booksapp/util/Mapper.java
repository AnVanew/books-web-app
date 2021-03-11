package ru.cbr.study.booksapp.util;

import ru.cbr.study.book.dto.AuthorDto;
import ru.cbr.study.book.dto.BookDto;
import ru.cbr.study.book.dto.CommentDto;
import ru.cbr.study.booksapp.entity.Author;
import ru.cbr.study.booksapp.entity.Book;
import ru.cbr.study.booksapp.entity.Comment;
import ru.cbr.study.booksapp.repository.BookRepository;

import java.util.List;

public class Mapper {

    private BookRepository bookRepository;

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
        author.setId(authorDto.getId());
        return author;
    }

    public static BookDto toDto(Book book){
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setBookName(book.getBookName());
        bookDto.setYear(book.getYear());
        bookDto.setAnnotation(book.getAnnotation());
        bookDto.setAuthorDto(Mapper.toDto(book.getAuthor()));
        return bookDto;
    }

    public static Book toEntity(BookDto bookDto){
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setBookName(bookDto.getBookName());
        book.setAnnotation(bookDto.getAnnotation());
        book.setYear(bookDto.getYear());
        book.setAuthor(toEntity(bookDto.getAuthorDto()));
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
        comment.setId(commentDto.getId());
        comment.setComment(commentDto.getComment());
        comment.setUserName(commentDto.getUserName());
        comment.setBook(Mapper.toEntity(commentDto.getBookDto()));
        return comment;
    }
}
