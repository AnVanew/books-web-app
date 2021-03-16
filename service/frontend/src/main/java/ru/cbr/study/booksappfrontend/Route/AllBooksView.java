package ru.cbr.study.booksappfrontend.Route;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.cbr.study.book.dto.AuthorDto;
import ru.cbr.study.book.dto.BookDto;
import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import static ru.cbr.study.book.references.References.*;


@Slf4j
@Route(value = "books", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class AllBooksView extends AppLayout{

    public static final String VIEW_NAME = "Books";
    Button createBook;
    VerticalLayout layout;
    Grid<BookDto> grid;

    @Value("${backend.endpoint}")
    private String backEndEndpoint;

    FormLayout authorSearchForm;
    FormLayout bookNameSearchForm;
    ComboBox<AuthorDto> select;
    TextField bookName;
    Button searchByAuthor;
    Button searchByName;

    public AllBooksView() {
    }

    @PostConstruct
    private void paint(){
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = backEndEndpoint + AUTHORS_CONT + ALL_AUTHORS_REF;
        ResponseEntity<AuthorDto[]> response = restTemplate.getForEntity(fooResourceUrl, AuthorDto[].class);
        List<AuthorDto> authors = Arrays.asList(response.getBody());
        select = new ComboBox<>("Select author");
        select.setItemLabelGenerator(AuthorDto::getNameAndSurname);
        select.setItems(authors);
        searchByAuthor = new Button("Search");
        authorSearchForm = new FormLayout();
        authorSearchForm.add(select, searchByAuthor);

        bookName = new TextField("Book name");
        searchByName = new Button("Search");
        bookNameSearchForm = new FormLayout();
        bookNameSearchForm.add(bookName, searchByName);

        createBook = new Button("Create book",  (a)->UI.getCurrent().navigate(BookManage.class,0));
        layout = new VerticalLayout();
        grid = new Grid<>();
        fillGrid();
        layout.add(bookNameSearchForm, authorSearchForm, createBook, grid);
        setContent(layout);
        authorSearch();
        bookSearch();
    }

    public void fillGrid(){

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = backEndEndpoint + BOOKS_CONT + ALL_BOOKS_REF;
        ResponseEntity<BookDto[]> response
                = restTemplate.getForEntity(fooResourceUrl, BookDto[].class);

        List<BookDto> bookDtos = Arrays.asList(response.getBody());

            grid.addColumn(BookDto::getBookName).setHeader("Book name");
            grid.addColumn(BookDto::getAuthor).setHeader("Author");
            grid.addColumn(BookDto::getAnnotation).setHeader("Annotation");
            grid.addColumn(BookDto::getYear).setHeader("Year");

            grid.addColumn(new NativeButtonRenderer<BookDto>("Update", book -> {
                UI.getCurrent().navigate(BookManage.class,book.getId());
            }));

            grid.addColumn(new NativeButtonRenderer<BookDto>("More...", book -> {
                UI.getCurrent().navigate(BookPage.class,book.getId());
            }));

            grid.addColumn(new NativeButtonRenderer<BookDto>("Delete", book -> {
                Dialog dialog = new Dialog();
                Button confirm = new Button("Delete");
                Button cancel = new Button("Cancel");
                dialog.add("Delete book?");
                dialog.add(confirm);
                dialog.add(cancel);
                confirm.addClickListener(clickEvent -> {

                    String entityUrl = backEndEndpoint + BOOKS_CONT + DELETE_BOOK_REF + "/" + book.getId() ;
                    restTemplate.delete(entityUrl);

                    dialog.close();
                    Notification notification = new Notification("Book is deleted",1000);
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.open();

                    ResponseEntity<BookDto[]> freshBookList
                            = restTemplate.getForEntity(fooResourceUrl, BookDto[].class);
                    List<BookDto> bookDtos2 = Arrays.asList(freshBookList.getBody());
                    grid.setItems(bookDtos2);

                });
                cancel.addClickListener(clickEvent -> {
                    dialog.close();
                });
                dialog.open();
            }));
            grid.setItems(bookDtos);
    }

    public void authorSearch(){
        searchByAuthor.addClickListener(clickEvent->{
            String entityUrl = backEndEndpoint + BOOKS_CONT + AUTHORS_BOOK_REF + "/" + select.getValue().getId();
            ResponseEntity<BookDto[]> response
                    = new RestTemplate().getForEntity(entityUrl, BookDto[].class);
            List<BookDto> bookDtos = Arrays.asList(response.getBody());
            grid.setItems(bookDtos);
            Notification notification = new Notification();
            notification.addDetachListener(detachEvent -> {
                UI.getCurrent().navigate(AllBooksView.class);
            });
            authorSearchForm.setEnabled(true);
            notification.open();
        });
    }

    public void bookSearch(){
        searchByName.addClickListener(clickEvent->{
            String entityUrl = backEndEndpoint + BOOKS_CONT + NAMED_BOOKS_REF + "/" + bookName.getValue();
            ResponseEntity<BookDto[]> response
                    = new RestTemplate().getForEntity(entityUrl, BookDto[].class);
            List<BookDto> bookDtos = Arrays.asList(response.getBody());
            grid.setItems(bookDtos);
            Notification notification = new Notification();
            notification.addDetachListener(detachEvent -> {
                UI.getCurrent().navigate(AllBooksView.class);
            });
            bookNameSearchForm.setEnabled(true);
            notification.open();
        });
    }
}
