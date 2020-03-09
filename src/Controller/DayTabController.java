package Controller;

import Model.Calendar;
import Model.Event;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.awt.*;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Optional;

public class DayTabController {
    Calendar model;

    public void setModel(Calendar newModel) {
        model = newModel;
    }

    public void handleAddEventClicked(ActionEvent actionEvent) {
        System.out.println("Add event clicked");

        Dialog<Event> dialog = new Dialog<>();

        dialog.setTitle("Create an Event");

        // Set the button types.
        ButtonType doneButtonType = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(doneButtonType, ButtonType.CANCEL);


        VBox addEventBox = new VBox();
        HBox startTimeBox = new HBox();
        ToggleGroup startAMPM = new ToggleGroup();
        HBox endTimeBox = new HBox();
        ToggleGroup endAMPM = new ToggleGroup();
        addEventBox.setPrefWidth(400);

        Label mainLabel = new Label("Enter new event information");
        mainLabel.setFont(new Font("Arial", 16));
        Label infoLabel = new Label("(Must enter all starred fields)");
        TextField title = new TextField();
        title.setPromptText("Title");
        TextField desc = new TextField();
        desc.setPromptText("Description");

        ObservableList<String> courses = FXCollections.observableArrayList("None");
        //TODO: Generate the list of courses using course model
        ComboBox<String> courseChoice = new ComboBox<>(courses);
        courseChoice.setValue("None");

        ObservableList<String> colours = FXCollections.observableArrayList("Green", "Blue",
                "Pink", "Red", "Orange", "Yellow");
        ComboBox<String> colourChoice = new ComboBox<>(colours);
        colourChoice.setValue("Green");

        DatePicker datePicker = new DatePicker(LocalDate.now());

        Spinner<Integer> startHour = new Spinner<>(1, 12, 1);
        startHour.setPrefWidth(70);
        Spinner<Integer> startMinute = new Spinner<>(0, 59, 0);
        startMinute.setPrefWidth(70);
        RadioButton amS = new RadioButton("AM");
        amS.setToggleGroup(startAMPM);
        amS.setSelected(true);
        RadioButton pmS = new RadioButton("PM");
        pmS.setToggleGroup(startAMPM);
        startTimeBox.getChildren().addAll(startHour, startMinute, amS, pmS);


        Spinner<Integer> endHour = new Spinner<>(1, 12, 2, 1);
        endHour.setPrefWidth(70);
        Spinner<Integer> endMinute = new Spinner<>(0, 59, 0, 1);
        endMinute.setPrefWidth(70);
        RadioButton amE = new RadioButton("AM");
        amE.setToggleGroup(endAMPM);
        amE.setSelected(true);
        RadioButton pmE = new RadioButton("PM");
        pmE.setToggleGroup(endAMPM);
        endTimeBox.getChildren().addAll(endHour, endMinute, amE, pmE);


        TextField location = new TextField();
        title.setPromptText("Location");


        addEventBox.getChildren().add(mainLabel);
        addEventBox.getChildren().add(infoLabel);
        addEventBox.getChildren().addAll(new Label("Title (*):"), title);
        Tooltip.install(title, new Tooltip("Hitting done with an empty title is the same as hitting cancel"));
        addEventBox.getChildren().addAll(new Label("Description:"), desc);
        addEventBox.getChildren().addAll(new Label("Course:"), courseChoice);
        addEventBox.getChildren().addAll(new Label("Colour:"), colourChoice);
        addEventBox.getChildren().addAll(new Label("Date: (*)"), datePicker);
        addEventBox.getChildren().addAll(new Label("Start time:"), startTimeBox);
        addEventBox.getChildren().addAll(new Label("End time:"), endTimeBox);
        Tooltip.install(endHour, new Tooltip("End time must be after start time. Will otherwise default to being " +
                "equal to start time."));
        Tooltip.install(endMinute, new Tooltip("End time must be after start time. Will otherwise default to being " +
                "equal to start time."));
        addEventBox.getChildren().addAll(new Label("Location:"), location);


        dialog.getDialogPane().setContent(addEventBox);

        // Request focus on the username field by default.
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
                if(pmS.isSelected()){
                    mod = 12;
                }
                LocalTime sTime = LocalTime.of(startHour.getValue()+mod, startMinute.getValue());

                if(amE.isSelected()){
                    mod = 0;
                }
                else if (pmE.isSelected()){
                    mod = 12;
                }
                LocalTime eTime = LocalTime.of(endHour.getValue()+mod, endMinute.getValue());

                if(sTime.isAfter(eTime)){
                    eTime = sTime;
                }

                Event e =  new Event(title.getText(), desc.getText(), null, c,
                datePicker.getValue().getDayOfMonth(), datePicker.getValue().getMonthValue(),
                        datePicker.getValue().getYear(), Time.valueOf(sTime), Time.valueOf(eTime), location.getText());

                if (!courseChoice.getValue().equals("None")){
                    e.setCourse(courseChoice.getValue());
                }

                return e;
            }
            return null;
        });

        Optional<Event> result = dialog.showAndWait();

        result.ifPresent(event -> {
            System.out.println(event.toString());

            try {
                model.insertEvent(event);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }
}












