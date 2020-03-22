package Controller;

import Model.Calendar;
import Model.CoursesModel;
import Model.Event;
import View.NewEventDialog;
import javafx.event.ActionEvent;
import javafx.scene.control.Dialog;
import java.util.Optional;

public class DayTabController {
    Calendar model;
    CoursesModel courseModel;

    public void setModel(Calendar newModel) {
        model = newModel;
    }

    public void setCoursesModel(CoursesModel newModel) {
        courseModel = newModel;
    }

    /**
     * Creates dialog to collect information for new event when New Event button clicked
     * @param actionEvent
     */
    public void handleAddEventClicked(ActionEvent actionEvent) {
        Dialog<Event> dialog = new NewEventDialog();    //TODO:Going to need to pass the courseModel or list of courses
                                                        // in order to generate list of courses

        Optional<Event> result = dialog.showAndWait();

        result.ifPresent(event -> {
            System.out.println(event.toString());

            model.insertEvent(event);
        });
    }
}



