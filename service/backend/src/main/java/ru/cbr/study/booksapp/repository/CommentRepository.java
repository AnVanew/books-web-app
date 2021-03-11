package ru.cbr.study.booksapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.cbr.study.booksapp.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

    Iterable<Comment> findCommentsByBook_Id(int bookId);
}
