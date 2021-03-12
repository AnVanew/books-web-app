package ru.cbr.study.booksapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.cbr.study.book.dto.AuthorDto;
import ru.cbr.study.booksapp.entity.Author;
import ru.cbr.study.booksapp.service.AuthorService;
import ru.cbr.study.booksapp.util.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping(path = "/addAuthor")
    public void addNewAuthor (@RequestBody AuthorDto authorDto) {
        Author author = Mapper.toEntity(authorDto);
        authorService.addAuthor(author);
        log.info("Save author");
    }

    @GetMapping("/allAuthors")
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAllAuthors().stream().map(Mapper::toDto).collect(Collectors.toList());
    }

    @DeleteMapping(path = "/deleteAuthor/{id}")
    public void deleteAuthor(@PathVariable int id){
        authorService.deleteAuthor(id);
        log.info("Delete author");
    }
}
