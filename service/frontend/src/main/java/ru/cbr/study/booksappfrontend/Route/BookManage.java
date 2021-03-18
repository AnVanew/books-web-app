package ru.cbr.study.booksappfrontend.Route;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
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
import ru.cbr.study.book.dto.MarksDto;

import java.util.Arrays;
import java.util.List;
import static ru.cbr.study.book.references.References.*;

@Slf4j
@Route("manageBook")
public class BookManage extends AppLayout implements HasUrlParameter<Integer> {

    Integer id;
    FormLayout bookForm;
    ComboBox<AuthorDto> select;
    TextField bookName;
    TextField annotation;
    NumberField year;
    Button saveBook;

    @Value("${backend.endpoint}")
    private String backEndEndpoint;


    public BookManage(){
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer bookId) {
        id = bookId;
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = backEndEndpoint + AUTHORS_CONT + ALL_AUTHORS_REF;
        ResponseEntity<AuthorDto[]> response
                = restTemplate.getForEntity(fooResourceUrl, AuthorDto[].class);
        List<AuthorDto> authors = Arrays.asList(response.getBody());

        select = new ComboBox<>("Select author");
        select.setItemLabelGenerator(AuthorDto::getNameAndSurname);
        select.setItems(authors);

        bookForm = new FormLayout();
        bookName = new TextField("Book name");
        annotation = new TextField("Annotation");
        year = new NumberField("Year");

        if (!id.equals(0)){
            addToNavbar(new H3("Update book"));
            String bookUrl = backEndEndpoint + BOOKS_CONT + BOOK_REF + "/" + id ;
            ResponseEntity<BookDto> responseEntity = restTemplate.getForEntity(bookUrl, BookDto.class);
            BookDto bookDto = responseEntity.getBody();
            bookName.setValue(bookDto.getBookName());
            annotation.setValue(bookDto.getAnnotation());
            year.setValue((double)bookDto.getYear());

            String AuthorResourceUrl = backEndEndpoint + AUTHORS_CONT + AUTHOR_BY_ID_REF + "/" + bookDto.getAuthorId();
            ResponseEntity<AuthorDto> response2 = restTemplate.getForEntity(AuthorResourceUrl, AuthorDto.class);
            AuthorDto authorDto = response2.getBody();

            select.setValue(authorDto);
        }
        else {
            addToNavbar(new H3("Create book"));
        }

        saveBook = new Button("Save");
        bookForm.add(bookName, annotation, year, select, saveBook);
        setContent(bookForm);

        fillForm();
    }

    public void fillForm(){
        saveBook.addClickListener(clickEvent->{
            BookDto bookDto = new BookDto();
            if(id!=0)bookDto.setId(id);
            bookDto.setBookName(bookName.getValue());
            bookDto.setAuthorId(select.getValue().getId());
            log.info("Form  " + bookDto.getAuthorId());
            bookDto.setAnnotation(annotation.getValue());
            bookDto.setYear(year.getValue().intValue());
            String entityUrl = backEndEndpoint + BOOKS_CONT + ADD_BOOK_REF;
            HttpEntity<BookDto> bookDtoHttpEntity = new HttpEntity<>(bookDto);
            new RestTemplate().postForEntity(entityUrl, bookDtoHttpEntity, BookDto.class);
            Notification notification = new Notification("Successful",1000);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.addDetachListener(detachEvent -> {
                UI.getCurrent().navigate(AllBooksView.class);
            });
            bookForm.setEnabled(false);
            notification.open();
        });
    }
}
