package ru.cbr.study.booksappfrontend.Route;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.cbr.study.book.dto.AuthorDto;
import static ru.cbr.study.book.references.References.*;
import javax.annotation.PostConstruct;
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

    @Value("${backend.endpoint}")
    private String backEndEndpoint;

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
                = backEndEndpoint + AUTHORS_CONT + ALL_AUTHORS_REF;
        ResponseEntity<AuthorDto[]> response
                = restTemplate.getForEntity(fooResourceUrl, AuthorDto[].class);
        List<AuthorDto> authorDto = Arrays.asList(response.getBody());
        grid.addColumn(AuthorDto::getName).setHeader("Name");
        grid.addColumn(AuthorDto::getSurname).setHeader("Surname");
        grid.setItems(authorDto);

        grid.addColumn(new NativeButtonRenderer<AuthorDto>("Delete", authorDto1 -> {
            Dialog dialog = new Dialog();
            Button confirm = new Button("Delete");
            Button cancel = new Button("Cancel");
            dialog.add("Delete author?");
            dialog.add(confirm);
            dialog.add(cancel);
            confirm.addClickListener(clickEvent -> {

                String entityUrl = backEndEndpoint + AUTHORS_CONT + DELETE_AUTHOR_REF + "/" + authorDto1.getId() ;
                restTemplate.delete(entityUrl);

                dialog.close();
                Notification notification = new Notification("Author is deleted",1000);
                notification.setPosition(Notification.Position.MIDDLE);
                notification.open();

                ResponseEntity<AuthorDto[]> response2
                        = restTemplate.getForEntity(fooResourceUrl, AuthorDto[].class);
                List<AuthorDto> authorDto2 = Arrays.asList(response2.getBody());
                grid.setItems(authorDto2);

            });
            cancel.addClickListener(clickEvent -> {
                dialog.close();
            });
            dialog.open();
        }));

        fillForm();
    }

    public void fillForm(){
        saveAuthor.addClickListener(clickEvent->{
            String entityUrl = backEndEndpoint + AUTHORS_CONT + ADD_AUTHOR_REF;
            AuthorDto authorDto = new AuthorDto();
            authorDto.setSurname(surname.getValue());
            authorDto.setName(name.getValue());
            HttpEntity<AuthorDto> authorDtoHttpEntity = new HttpEntity<>(authorDto);
            new RestTemplate().postForEntity(entityUrl, authorDtoHttpEntity, AuthorDto.class);

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
