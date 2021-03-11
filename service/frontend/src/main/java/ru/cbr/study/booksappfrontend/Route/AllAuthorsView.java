package ru.cbr.study.booksappfrontend.Route;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.cbr.study.book.dto.AuthorDto;


import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route(value = "authors", layout = MainLayout.class)
public class AllAuthorsView extends AppLayout {
    public static final String VIEW_NAME = "Authors";
    VerticalLayout layout;
    Grid<AuthorDto> grid;
    private FormLayout formLayout;
    TextField name;
    TextField surname;
    Button saveAuthor;

    public AllAuthorsView() {

        formLayout = new FormLayout();
        name = new TextField("Name");
        surname = new TextField("Surname");
        saveAuthor = new Button("Save");
        formLayout.add(name, surname, saveAuthor);

        layout = new VerticalLayout();
        grid = new Grid<>();
        layout.add(formLayout, grid);
        setContent(layout);
    }

    @PostConstruct
    public void fillGrid(){
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:80/authors/allAuthors";
        ResponseEntity<AuthorDto[]> response
                = restTemplate.getForEntity(fooResourceUrl, AuthorDto[].class);
        List<AuthorDto> authorDto = Arrays.asList(response.getBody());
        grid.addColumn(AuthorDto::getName).setHeader("Name");
        grid.addColumn(AuthorDto::getSurname).setHeader("Surname");
        grid.setItems(authorDto);

        fillForm();
    }

    public void fillForm(){
        saveAuthor.addClickListener(clickEvent->{
            //Вызов метода добавления автора
            Notification notification = new Notification("Save author",1000);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.addDetachListener(detachEvent -> {
                UI.getCurrent().navigate(AllBooksView.class);
            });
            formLayout.setEnabled(false);
            notification.open();
        });
    }
}
