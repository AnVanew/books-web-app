package ru.cbr.study.booksappfrontend.Route;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.cbr.study.book.dto.BookDto;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;


@Route(value = "books", layout = MainLayout.class)
public class AllBooksView extends AppLayout{

    Class<List<BookDto>> bookDtoListClass = (Class<List<BookDto>>) (Class<?>) List.class;


    public static final String VIEW_NAME = "Books";
    VerticalLayout layout;
    Grid<BookDto> grid;

    public AllBooksView() {
        layout = new VerticalLayout();
        grid = new Grid<>();
        layout.add(grid);
        setContent(layout);
    }

    @PostConstruct
    public void fillGrid(){

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:80/books/allBooks";
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

                    //УДАЛЕНИЕ
                    //contactRepository.delete(contact);

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
}
