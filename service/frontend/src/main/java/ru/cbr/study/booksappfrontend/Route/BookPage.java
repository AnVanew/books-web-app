package ru.cbr.study.booksappfrontend.Route;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.cbr.study.book.dto.BookDto;
import ru.cbr.study.book.dto.CommentDto;
import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import static ru.cbr.study.book.references.References.*;

@Slf4j
@Route(value = "BookPage", layout = MainLayout.class)
public class BookPage extends AppLayout implements HasUrlParameter<Integer> {

    private VerticalLayout layout;
    private FormLayout formLayout;
    private int id;
    private Label name;
    private Label surname;
    private Label bookName;
    private Label annotation;
    private Label year;
    TextField userName;
    TextField comment;
    Button saveComment;
    private Grid<CommentDto> commentDtoGrid;
    private BookDto bookDto;

    @Value("${backend.endpoint}")
    private String backEndEndpoint;

    public BookPage(){
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer bookId) {
        id = bookId;
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = backEndEndpoint + BOOKS_CONT + BOOK_REF + "/" + id ;
        ResponseEntity<BookDto> responseEntity = restTemplate.getForEntity(fooResourceUrl, BookDto.class);
        bookDto = responseEntity.getBody();

        formLayout = new FormLayout();
        userName = new TextField("User");
        comment = new TextField("Comment");
        saveComment = new Button("Save");
        formLayout.add(userName, comment, saveComment);

        bookName = new Label("Book name : " + bookDto.getBookName());
        annotation = new Label("Annotation : " + bookDto.getAnnotation());
        name = new Label("Name : " + bookDto.getAuthorDto().getName());
        surname = new Label("Surname : " + bookDto.getAuthorDto().getSurname());
        year = new Label("Year : " + String.valueOf(bookDto.getYear()));

        layout = new VerticalLayout();
        commentDtoGrid = new Grid<>();
        layout.add(bookName, name, surname, annotation, year , formLayout, commentDtoGrid);
        setContent(layout);
        fillGrid();
        fillForm();
    }

    public void fillGrid(){
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = backEndEndpoint + COMMENTS_CONT + ALL_COMMENTS_REF + "/" + id;
        ResponseEntity<CommentDto[]> response
                = restTemplate.getForEntity(fooResourceUrl, CommentDto[].class);
        List<CommentDto> commentDtos = Arrays.asList(response.getBody());
        if (!commentDtos.isEmpty()){
            commentDtoGrid.addColumn(CommentDto::getUserName).setHeader("User");
            commentDtoGrid.addColumn(CommentDto::getComment).setHeader("Comment");
            commentDtoGrid.setItems(commentDtos);
        }
    }

    public void fillForm(){
        saveComment.addClickListener(clickEvent->{

            CommentDto commentDto = new CommentDto();
            commentDto.setComment(comment.getValue());
            commentDto.setUserName(userName.getValue());
            commentDto.setBookDto(bookDto);
            String fooResourceUrl = backEndEndpoint + COMMENTS_CONT + ADD_COMMENT_REF;
            HttpEntity<CommentDto> commentDtoHttpEntity = new HttpEntity<>(commentDto);
            new RestTemplate().postForEntity(fooResourceUrl, commentDtoHttpEntity, CommentDto.class);

            Notification notification = new Notification("New comment",1000);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.addDetachListener(detachEvent -> {
                UI.getCurrent().navigate(AllBooksView.class);
            });
            formLayout.setEnabled(false);
            notification.open();
        });
    }

}
