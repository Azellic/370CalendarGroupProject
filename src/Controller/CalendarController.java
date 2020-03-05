package Controller;

import Model.Calendar;
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
        model.setSelectedDay();
    }
}
