package ru.cbr.study.booksapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.cbr.study.book.dto.CommentDto;
import ru.cbr.study.booksapp.entity.Comment;
import ru.cbr.study.booksapp.service.CommentService;
import ru.cbr.study.booksapp.util.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(path = "/addComment")
    public void addComment(@RequestBody CommentDto commentDto){
        Comment comment = Mapper.toEntity(commentDto);
        commentService.addComment(comment);
    }

    @GetMapping(path = "/bookComments")
    public List<CommentDto> getAllBookComments(@RequestHeader int bookId){
        return commentService.getAllBooksComments(bookId).stream().map(Mapper::toDto).collect(Collectors.toList());
    }
}
