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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.cbr.study.book.dto.AuthorDto;
import ru.cbr.study.book.dto.BookDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
<<<<<<< HEAD
import static ru.cbr.study.book.references.References.*;
=======
>>>>>>> fd063232679caee30acf43846bfa195853c8bc9b

@Route("manageBook")
public class BookManage extends AppLayout implements HasUrlParameter<Integer> {

    Integer id;
    FormLayout bookForm;
    ComboBox<AuthorDto> select;
    TextField bookName;
    TextField annotation;
    NumberField year;
    Button saveBook;


    public BookManage(){
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:80/authors/allAuthors";
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
        saveBook = new Button("Save");
        bookForm.add(bookName, annotation, year, select, saveBook);
        setContent(bookForm);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer bookId) {
        id = bookId;
        if (!id.equals(0)){
            addToNavbar(new H3("Update book"));
        }
        else {
            addToNavbar(new H3("Create book"));
        }
        fillForm();
    }

    public void fillForm(){
        saveBook.addClickListener(clickEvent->{
            BookDto bookDto = new BookDto();
            if(id!=0)bookDto.setId(id);
            bookDto.setBookName(bookName.getValue());
            bookDto.setAuthorDto(select.getValue());
            bookDto.setAnnotation(annotation.getValue());
            bookDto.setYear(year.getValue().intValue());
            String entityUrl = "http://localhost:80/books/addBook";
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
