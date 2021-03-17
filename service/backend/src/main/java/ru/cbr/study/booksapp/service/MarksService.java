package ru.cbr.study.booksapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cbr.study.booksapp.entity.Marks;
import ru.cbr.study.booksapp.repository.MarksRepository;

@Service
public class MarksService {

    private final MarksRepository marksRepository;

    @Autowired
    public MarksService(MarksRepository marksRepository) {
        this.marksRepository = marksRepository;
    }

    public int getLikes(int book_id){
        return marksRepository.getLikes(book_id);
    }

    public int getDislikes(int book_id){
        return marksRepository.getDislikes(book_id);
    }

    public Marks getMarks(int book_id){
        return marksRepository.findById(book_id).get();
    }

    public void like(int book_id){
        marksRepository.setLike(book_id);
    }

    public void dislike(int book_id){
        marksRepository.setDislike(book_id);
    }
}
