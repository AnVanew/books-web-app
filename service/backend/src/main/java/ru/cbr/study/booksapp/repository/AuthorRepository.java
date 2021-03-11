package ru.cbr.study.booksapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.cbr.study.booksapp.entity.Author;

public interface AuthorRepository extends CrudRepository<Author, Integer> {
}