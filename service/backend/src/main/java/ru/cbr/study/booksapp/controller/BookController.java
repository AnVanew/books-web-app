package ru.cbr.study.booksapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.cbr.study.book.dto.BookDto;
import ru.cbr.study.booksapp.entity.Book;
import ru.cbr.study.booksapp.service.BookService;
import ru.cbr.study.booksapp.service.JmsService;
import ru.cbr.study.booksapp.util.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/books")
@Tag(name="Books controller", description="CRUD with books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final JmsService jmsService;
    private final ObjectMapper objectMapper;

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

    @Transactional
    @PostMapping(path = "/addBook")
    public void addNewBook(@RequestBody BookDto bookDto) throws JsonProcessingException {
        Book book = Mapper.toEntity(bookDto);
        bookService.addBook(book);
        jmsService.sendToQueue(bookDto);
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


