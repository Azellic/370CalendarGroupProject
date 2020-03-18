package Controller;

import Model.Calendar;
import Model.Event;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.awt.*;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class DayTabController {
    Calendar model;

    public void setModel(Calendar newModel) {
        model = newModel;
    }

    /**
     * Creates dialog to collect information for new event when New Event button clicked
     * @param actionEvent
     */
    public void handleAddEventClicked(ActionEvent actionEvent) {
        Dialog<Event> dialog = createNewEventDialog();

        Optional<Event> result = dialog.showAndWait();

        result.ifPresent(event -> {
            try {
                model.insertEvent(event);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    private Dialog<Event> createNewEventDialog(){
        Dialog<Event> dialog = new Dialog<>();
        dialog.setTitle("Create an Event");

        ButtonType doneButtonType = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(doneButtonType, ButtonType.CANCEL);

        VBox addEventBox = new VBox();
        addEventBox.setPrefWidth(400);

        Label mainLabel = new Label("Enter new event information");
        mainLabel.setFont(new Font("Arial", 16));
        TextField title = new TextField();
        title.setPromptText("Title");
        Tooltip.install(title, new Tooltip("Hitting done with an empty title is the same as hitting cancel"));
        TextField desc = new TextField();
        desc.setPromptText("Description");

        ObservableList<String> courses = FXCollections.observableArrayList("None");
        //TODO: Generate the list of courses using course model
        ComboBox<String> courseChoice = new ComboBox<>(courses);
        courseChoice.setValue("None");

        ObservableList<String> colours = FXCollections.observableArrayList("Green", "Blue", "Red",
                "Orange", "Yellow");
        ComboBox<String> colourChoice = new ComboBox<>(colours);
        colourChoice.setValue("Green");

        DatePicker datePicker = new DatePicker(LocalDate.now());

        ToggleGroup startAMPM = new ToggleGroup(), endAMPM = new ToggleGroup();
        Spinner<Integer> startHour = new Spinner<>(1, 12, 1, 1);
        Spinner<Integer> startMinute = new Spinner<>(0, 59, 0);
        Spinner<Integer> endHour = new Spinner<>(1, 12, 2, 1);
        Spinner<Integer> endMinute = new Spinner<>(0, 59, 0, 1);
        HBox startTimeBox = createTimeBox(startHour, startMinute, startAMPM);
        HBox endTimeBox = createTimeBox(endHour, endMinute, endAMPM);
        Tooltip.install(endHour, new Tooltip("End time must be after start time. Will otherwise default to being " +
                "equal to start time."));
        Tooltip.install(endMinute, new Tooltip("End time must be after start time. Will otherwise default to " +
                "being equal to start time."));
        TextField location = new TextField();
        location.setPromptText("Location");

        addEventBox.getChildren().addAll(mainLabel, new Label("(Must enter all starred fields)"),
                new Label("Title (*):"), title, new Label("Description:"), desc,
                new Label("Course:"), courseChoice, new Label("Colour:"), colourChoice,
                new Label("Date: (*)"), datePicker, new Label("Start time:"), startTimeBox,
                new Label("End time:"), endTimeBox, new Label("Location:"), location);

        dialog.getDialogPane().setContent(addEventBox);

        Platform.runLater(() -> title.requestFocus());

        // Convert the result to an event when the done button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == doneButtonType) {
                Color c;
                try {
                    Field field = Class.forName("java.awt.Color").getField(colourChoice.getValue());
                    c = (Color)field.get(null);
                } catch (Exception e) {
                    c = Color.GREEN; // Not defined
                }

                if(title.getText().isEmpty()){
                    return null;
                }

                int mod = 0;
                if(((RadioButton)startAMPM.getSelectedToggle()).getText().equals("PM")){
                    mod = 12;
                }
                LocalTime sTime = LocalTime.of(startHour.getValue()+mod, startMinute.getValue());

                mod = 0;
                if (((RadioButton)endAMPM.getSelectedToggle()).getText().equals("PM")){
                    mod = 12;
                }
                LocalTime eTime = LocalTime.of(endHour.getValue()+mod, endMinute.getValue());

                if(sTime.isAfter(eTime)){
                    eTime = sTime;
                }

                Event newEvent =  new Event(title.getText(), desc.getText(), null, c,
                        datePicker.getValue().getDayOfMonth(), datePicker.getValue().getMonthValue(),
                        datePicker.getValue().getYear(), Time.valueOf(sTime), Time.valueOf(eTime), location.getText());

                if (!courseChoice.getValue().equals("None")){
                    newEvent.setCourse(courseChoice.getValue());
                }

                return newEvent;
            }
            return null;
        });
        return dialog;
    }

    private HBox createTimeBox(Spinner<Integer> hour, Spinner<Integer> minute, ToggleGroup groupAMPM) {
        HBox timeBox = new HBox();
        hour.setPrefWidth(70);
        minute.setPrefWidth(70);
        RadioButton am = new RadioButton("AM");
        am.setToggleGroup(groupAMPM);
        am.setSelected(true);
        RadioButton pm = new RadioButton("PM");
        pm.setToggleGroup(groupAMPM);
        timeBox.getChildren().addAll(hour, minute, am, pm);
        return timeBox;
    }
}



