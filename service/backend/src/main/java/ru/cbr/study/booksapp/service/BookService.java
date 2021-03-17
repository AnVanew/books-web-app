package ru.cbr.study.booksapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cbr.study.book.dto.BookDto;
import ru.cbr.study.booksapp.entity.Author;
import ru.cbr.study.booksapp.entity.Book;
import ru.cbr.study.booksapp.entity.Marks;
import ru.cbr.study.booksapp.repository.BookRepository;
import ru.cbr.study.booksapp.repository.MarksRepository;
import ru.cbr.study.booksapp.util.Mapper;

import java.util.List;

@Slf4j
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final MarksRepository marksRepository;

    @Autowired
    public BookService(BookRepository bookRepository, MarksRepository marksRepository) {
        this.bookRepository = bookRepository;
        this.marksRepository = marksRepository;
    }

    public List<Book> getAllBooks(){
        return (List<Book>) bookRepository.findAll();
    }

    public List<Book> getAuthorsBook(int authorId){
        return (List<Book>) bookRepository.findBooksByAuthor_Id(authorId);
    }

    public void addBook(Book book){
        Integer id = book.getId();
        bookRepository.save(book);
        if(id == null) {
            Marks marks = new Marks();
            marks.setBook(book);
            marks.setLikes(0);
            marks.setDislikes(0);
            marksRepository.save(marks);
        }
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
