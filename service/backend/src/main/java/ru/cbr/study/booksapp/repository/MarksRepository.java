package ru.cbr.study.booksapp.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.cbr.study.booksapp.entity.Marks;

public interface MarksRepository extends CrudRepository<Marks, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE Marks SET likes = likes +1 WHERE book_id = :book_id")
    public void setLike(@Param("book_id") int book_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Marks SET dislikes = dislikes +1 WHERE book_id = :book_id")
    public void setDislike(@Param("book_id") int book_id);

    @Query(value = "SELECT likes FROM Marks WHERE book_id = ?1")
    public int getLikes(int book_id);

    @Query(value = "SELECT dislikes FROM Marks WHERE book_id = ?1")
    public int getDislikes(int book_id);

}
