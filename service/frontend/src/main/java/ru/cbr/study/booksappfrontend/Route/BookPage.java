package ru.cbr.study.booksappfrontend.Route;



import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.BackEndDataProvider;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.cbr.study.book.dto.AuthorDto;
import ru.cbr.study.book.dto.BookDto;
import ru.cbr.study.book.dto.CommentDto;
import ru.cbr.study.book.dto.MarksDto;

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
    private TextField userName;
    private TextField comment;
    private Button saveComment;
    private Grid<CommentDto> commentDtoGrid;
    private Grid<MarksDto> marksDtoGrid;
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

        String AuthorResourceUrl = backEndEndpoint + AUTHORS_CONT + AUTHOR_BY_ID_REF + "/" + bookDto.getAuthorId();
        ResponseEntity<AuthorDto> response = restTemplate.getForEntity(AuthorResourceUrl, AuthorDto.class);
        AuthorDto authorDto = response.getBody();

        bookName = new Label("Book name : " + bookDto.getBookName());
        annotation = new Label("Annotation : " + bookDto.getAnnotation());
        name = new Label("Name : " + authorDto.getName());
        surname = new Label("Surname : " + authorDto.getSurname());
        year = new Label("Year : " + String.valueOf(bookDto.getYear()));

        layout = new VerticalLayout();
        commentDtoGrid = new Grid<>();
        marksDtoGrid = new Grid<>();
        layout.add(bookName, name, surname, annotation, year , formLayout, marksDtoGrid, commentDtoGrid);
        setContent(layout);
        fillGrid();
        fillMarksGrid();
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

    public void fillMarksGrid(){
        RestTemplate restTemplate = new RestTemplate();
        String marksResourceUrl = backEndEndpoint + MARKS_CONT + ALL_MARKS_REF + "/" + id;
        ResponseEntity<MarksDto> response = restTemplate.getForEntity(marksResourceUrl,MarksDto.class);

            marksDtoGrid.addColumn(MarksDto::getLikes).setHeader("Likes");
            marksDtoGrid.addColumn(MarksDto::getDislikes).setHeader("Dislikes");
            marksDtoGrid.addColumn(new NativeButtonRenderer<MarksDto>("Like", clickEvent->{
                String entityUrl = backEndEndpoint + MARKS_CONT + LIKE_REF + "/" +id; new RestTemplate().put(entityUrl, MarksDto.class);
                UI.getCurrent().getPage().reload();}));
            marksDtoGrid.addColumn(new NativeButtonRenderer<MarksDto>("Dislike", clickEvent->{
                String entityUrl = backEndEndpoint + MARKS_CONT + DISLIKE_REF + "/" +id; new RestTemplate().put(entityUrl, MarksDto.class);
                UI.getCurrent().getPage().reload();}));
            marksDtoGrid.setItems(response.getBody());

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
