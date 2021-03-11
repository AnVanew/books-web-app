package ru.cbr.study.booksappfrontend.Route;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route(value = "")
public class MainPage extends AppLayout {

    VerticalLayout layout;
    Grid<String> grid;

//s;lkf;sk
    public MainPage(){
        layout = new VerticalLayout();
        grid = new Grid<>();
        layout.add(grid);
        addToNavbar(new H3("Список книг"));
        setContent(layout);
    }

}
