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
import ru.cbr.study.book.dto.AuthorDto;
import ru.cbr.study.book.dto.BookDto;

import java.util.ArrayList;
import java.util.List;

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
///////////
        AuthorDto authorDto = new AuthorDto();
        authorDto.setName("sdasd");
        authorDto.setSurname("asdas");
        authorDto.setId(1);

        AuthorDto authorDto2 = new AuthorDto();
        authorDto2.setName("qwe");
        authorDto2.setSurname("qweqwer");
        authorDto2.setId(2);
////////////////
        List<AuthorDto>  authors = new ArrayList<>();
        authors.add(authorDto);
        authors.add(authorDto2);

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
            //bookDto.setAuthorDto(select.getValue());
            bookDto.setAnnotation(annotation.getValue());
            bookDto.setYear(year.getValue().intValue());
            //Вызов метода апдейта книги
//            contact.setFirstName(firstName.getValue());
//            contact.setSecondName(secondName.getValue());
//            contact.setFatherName(fatherName.getValue());
//            contact.setEmail(email.getValue());
//            contact.setNumberPhone(numberPhone.getValue());
//            contactRepository.save(contact);
            Notification notification = new Notification("Update successful",1000);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.addDetachListener(detachEvent -> {
                UI.getCurrent().navigate(AllBooksView.class);
            });
            bookForm.setEnabled(false);
            notification.open();
        });
    }
}
