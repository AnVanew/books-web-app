package ru.cbr.study.booksapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.cbr.study.booksapp.entity.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {

    Iterable<Book> findBooksByAuthor_Id(int authorId);
    Iterable<Book> findBooksByBookName(String bookName);

}
