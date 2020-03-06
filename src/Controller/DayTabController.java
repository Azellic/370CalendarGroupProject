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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.awt.*;
import java.lang.reflect.Field;
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
        addEventBox.setPrefWidth(300);

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
        TextField location = new TextField();
        title.setPromptText("Location");


        addEventBox.getChildren().add(mainLabel);
        addEventBox.getChildren().add(infoLabel);
        addEventBox.getChildren().add(new Label("Title (*):"));
        addEventBox.getChildren().add(title);
        addEventBox.getChildren().add(new Label("Description:"));
        addEventBox.getChildren().add(desc);
        addEventBox.getChildren().add(new Label("Course:"));
        addEventBox.getChildren().add(courseChoice);
        addEventBox.getChildren().add(new Label("Colour:"));
        addEventBox.getChildren().add(colourChoice);
        addEventBox.getChildren().add(new Label("Date: (*)"));
        addEventBox.getChildren().add(datePicker);
        addEventBox.getChildren().add(new Label("Location:"));
        addEventBox.getChildren().add(location);


        dialog.getDialogPane().setContent(addEventBox);

        // Request focus on the username field by default.
        Platform.runLater(() -> title.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == doneButtonType) {
                Color c;
                try {
                    Field field = Class.forName("java.awt.Color").getField(colourChoice.getValue());
                    c = (Color)field.get(null);
                } catch (Exception e) {
                    c = Color.GREEN; // Not defined
                }

                Event e =  new Event(title.getText(), desc.getText(), null, c,
                datePicker.getValue().getDayOfMonth(), datePicker.getValue().getMonthValue(),
                        datePicker.getValue().getYear(), Time.valueOf(LocalTime.now()),
                        Time.valueOf(LocalTime.now()) , location.getText());

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
            if(event.getTitle() != ""){
                //model.addEvent(event);
                //TODO:connect this with model
            }
        });

    }
}












