package ru.cbr.study.booksappfrontend.Route;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import javafx.scene.control.ComboBox;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.cbr.study.book.dto.AuthorDto;
import ru.cbr.study.book.dto.BookDto;
import ru.cbr.study.book.dto.CommentDto;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public BookPage(){
        /////////
        AuthorDto authorDto = new AuthorDto();
        authorDto.setName("sdasd");
        authorDto.setSurname("asdas");
        authorDto.setId(1);
        BookDto bookDto = new BookDto();
        bookDto.setAnnotation("sdasd");
        bookDto.setBookName("Hello");
        bookDto.setYear(12);
        //bookDto.setAuthorDto(authorDto);
        bookDto.setId(1);
        /////////

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
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer bookId) {
        id = bookId;
        fillForm();
    }

    @PostConstruct
    public void fillGrid(){

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:80/comments/bookComments";
        ResponseEntity<CommentDto[]> response
                = restTemplate.getForEntity(fooResourceUrl, CommentDto[].class);
        List<CommentDto> commentDtos = Arrays.asList(response.getBody());
        commentDtoGrid.addColumn(CommentDto::getUserName).setHeader("User");
        commentDtoGrid.addColumn(CommentDto::getComment).setHeader("Comment");
        commentDtoGrid.setItems(commentDtos);
    }

    public void fillForm(){
        saveComment.addClickListener(clickEvent->{
            //Вызов метода добавления коммента
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
