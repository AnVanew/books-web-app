package ru.cbr.study.booksapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.cbr.study.book.dto.CommentDto;
import ru.cbr.study.booksapp.entity.Book;
import ru.cbr.study.booksapp.entity.Comment;
import ru.cbr.study.booksapp.repository.CommentRepository;
import ru.cbr.study.booksapp.util.Mapper;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void addComment(Comment comment){
        commentRepository.save(comment);
    }

    public List<Comment> getAllBooksComments(int bookId){
        return (List<Comment>) commentRepository.findCommentsByBook_Id(bookId);
    }
}
