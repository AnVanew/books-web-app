package ru.cbr.study.booksapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cbr.study.book.dto.BookDto;
import ru.cbr.study.booksapp.entity.Author;
import ru.cbr.study.booksapp.entity.Book;
import ru.cbr.study.booksapp.repository.BookRepository;
import ru.cbr.study.booksapp.util.Mapper;

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

    public void deleteBook(int id){
        bookRepository.deleteById(id);
    }

    public Book getBookById(int id){
        return bookRepository.findById(id).get();
    }

    public List<Book> getBookWithSameName(String bookName){
        return (List<Book>) bookRepository.findBooksByBookName(bookName);
    }

}
