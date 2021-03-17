package ru.cbr.study.booksapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.cbr.study.book.dto.MarksDto;
import ru.cbr.study.booksapp.entity.Marks;
import ru.cbr.study.booksapp.service.MarksService;
import ru.cbr.study.booksapp.util.Mapper;

@RestController
@RequestMapping("/marks")
@Tag(name="Marks controller", description="Like, dislike and get all marks.")
public class MarksController {

    private final MarksService marksService;

    @Autowired
    public MarksController(MarksService marksService) {
        this.marksService = marksService;
    }

    @GetMapping("/getLikes/{book_id}")
    public int getLikes(@PathVariable int book_id){
        return marksService.getLikes(book_id);
    }

    @GetMapping("/getDislikes/{book_id}")
    public int getDislikes(@PathVariable int book_id){
        return marksService.getDislikes(book_id);
    }

    @GetMapping("/getMarks/{book_id}")
    public MarksDto getMarks(@PathVariable int book_id){
        return Mapper.toDto(marksService.getMarks(book_id));
    }

    @PutMapping("/like/{book_id}")
    public void like(@PathVariable int book_id){
        marksService.like(book_id);
    }

    @PutMapping("/dislike/{book_id}")
    public void dislike(@PathVariable int book_id){
        marksService.dislike(book_id);
    }
}
