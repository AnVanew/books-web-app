package ru.cbr.study.booksapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.cbr.study.book.dto.BookDto;
import ru.cbr.study.booksapp.entity.Book;
import ru.cbr.study.booksapp.service.BookService;
import ru.cbr.study.booksapp.util.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

    @GetMapping(path = "/authorBooks/{authorId}")
    public List<BookDto> getAuthorBooks(@PathVariable int authorId){
        return bookService.getAuthorsBook(authorId).stream().map(Mapper::toDto).collect(Collectors.toList());
    }

    @GetMapping(path = "/namedBooks/{bookName}")
    public List<BookDto> getBooksWithSameName(@PathVariable String bookName){
        return bookService.getBookWithSameName(bookName).stream().map(Mapper::toDto).collect(Collectors.toList());
    }

    @GetMapping(path = "/book/{id}")
    public BookDto getBook(@PathVariable int id){
        return Mapper.toDto(bookService.getBookById(id));
    }

    @PostMapping(path = "/addBook")
    public void addNewBook(@RequestBody BookDto bookDto){
        Book book = Mapper.toEntity(bookDto);
        bookService.addBook(book);
    }

    @DeleteMapping(path = "/deleteBook/{id}")
    public void deleteBook(@PathVariable int id){
        bookService.deleteBook(id);
    }

    @PutMapping(path = "/updateBook")
    public void updateBook(@RequestBody BookDto bookDto){
        Book book = Mapper.toEntity(bookDto);
        bookService.addBook(book);
    }

}


