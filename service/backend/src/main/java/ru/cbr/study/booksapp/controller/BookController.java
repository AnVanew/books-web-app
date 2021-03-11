package ru.cbr.study.booksapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.cbr.study.book.dto.BookDto;
import ru.cbr.study.booksapp.entity.Book;
import ru.cbr.study.booksapp.service.BookService;
import ru.cbr.study.booksapp.util.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping (path = "/allBooks")
    public List<BookDto> getAllBooks(){
        return bookService.getAllBooks().stream().map(Mapper::toDto).collect(Collectors.toList());
    }

    @GetMapping(path = "/authorBooks")
    public List<BookDto> getAuthorBooks(@RequestHeader int authorId){
        return bookService.getAuthorsBook(authorId).stream().map(Mapper::toDto).collect(Collectors.toList());
    }

    @PostMapping(path = "/addBook")
    public void addNewBook(@RequestBody BookDto bookDto){
        Book book = Mapper.toEntity(bookDto);
        bookService.addBook(book);
    }

    @DeleteMapping(path = "/deleteBook")
    public void deleteBook(@RequestBody BookDto bookDto){
        Book book = Mapper.toEntity(bookDto);
        bookService.deleteBook(book);
    }

    @PutMapping(path = "/updateBook")
    public void updateBook(@RequestHeader int bookId,
                           @RequestHeader String bookName,
                           @RequestHeader String annotation,
                           @RequestHeader int year){
        bookService.updateBook(bookId, annotation, bookName, year);
    }

}


