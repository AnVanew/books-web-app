package ru.cbr.study.booksapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cbr.study.booksapp.entity.Author;
import ru.cbr.study.booksapp.entity.Book;
import ru.cbr.study.booksapp.repository.BookRepository;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks(){
        return (List<Book>) bookRepository.findAll();
    }

    public List<Book> getAuthorsBook(int authorId){
        return (List<Book>) bookRepository.findBooksByAuthor_Id(authorId);
    }

    public void addBook(Book book){
        bookRepository.save(book);
    }

    public void deleteBook(Book book){
        bookRepository.delete(book);
    }

    public void updateBook(int bookId, String annotation, String bookName, int year){
        Book book;
        book = bookRepository.findById(bookId).get();
        book.setId(bookId);
        book.setAnnotation(annotation);
        book.setBookName(bookName);
        book.setYear(year);
        bookRepository.save(book);
    }



}
