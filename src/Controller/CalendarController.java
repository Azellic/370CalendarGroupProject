package Controller;

import Model.Calendar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import Calendar.AnchorPaneNode;

import java.time.LocalDate;

public class CalendarController {
    // Get the pane to put the calendar on
    @FXML
    public Pane calendarPane;

    Calendar model;

    public void setModel(Calendar newModel) {
        model = newModel;
    }

    public void handleDayClicked(MouseEvent event) {
        LocalDate date = ((AnchorPaneNode)event.getSource()).getDate();
        System.out.println(date);
        //model.setSelectedDay(date);
    }

    public void previousMonth(ActionEvent actionEvent) {
        System.out.println("-1");
        //model.changeMonthBy(-1);
    }

    public void nextMonth(ActionEvent actionEvent) {
        System.out.println("+1");
        //model.changeMonthBy(+1);
    }
}
