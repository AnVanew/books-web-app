package ru.cbr.study.booksapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cbr.study.booksapp.entity.Author;
import ru.cbr.study.booksapp.repository.AuthorRepository;

import java.util.List;

@Slf4j
@Service
public class AuthorService {

    AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors(){
        return (List<Author>) authorRepository.findAll();
    }

    public void addAuthor(Author author){
        authorRepository.save(author);
    }

    public void deleteAuthor(Author author){
        authorRepository.delete(author);
    }

}
