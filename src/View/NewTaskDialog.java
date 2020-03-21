package View;

import Model.Task;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.awt.*;
import java.lang.reflect.Field;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class NewTaskDialog extends InputDialog {
    private final ButtonType doneButtonType;
    VBox addTaskBox;
    TextField title;
    TextField desc;
    ComboBox<String> courseChoice, colourChoice;
    ToggleGroup groupAMPM;
    DatePicker datePicker;
    Spinner<Integer> hour, minute;

    public NewTaskDialog(ObservableList<String> courses) {
        this.setTitle("Create A Task");

        doneButtonType = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(doneButtonType, ButtonType.CANCEL);

        addTaskBox = new VBox();
        addTaskBox.setPrefWidth(400);

        Label mainLabel = new Label("Enter new event information");
        mainLabel.setFont(new Font("Arial", 16));
        title = new TextField();
        title.setPromptText("Title");
        Tooltip.install(title, new Tooltip("Hitting done with an empty title is the same as hitting cancel"));

        //TODO: Generate the list of courses using course model
        courseChoice = new ComboBox<>(courses);
        courseChoice.setValue("None");
        ObservableList<String> colours = FXCollections.observableArrayList("Green", "Blue", "Red",
                "Orange", "Yellow");
        colourChoice = new ComboBox<>(colours);
        colourChoice.setValue("Green");
        desc = new TextField();
        desc.setPromptText("Description");

        datePicker = new DatePicker(LocalDate.now());
        datePicker.setEditable(false);
        groupAMPM = new ToggleGroup();
        hour = new Spinner<>(1, 12, 2, 1);
        minute = new Spinner<>(0, 59, 0, 1);
        HBox endTimeBox = createTimeBox(hour, minute, groupAMPM);

        addTaskBox.getChildren().addAll(mainLabel, new Label("(Must enter all starred fields)"),
                new Label("Title (*):"), title, new Label("Description:"), desc,
                new Label("Course:"), courseChoice, new Label("Colour:"), colourChoice,
                new Label("Date: (*)"), datePicker, new Label("Time:"), endTimeBox);

        this.getDialogPane().setContent(addTaskBox);

        Platform.runLater(() -> title.requestFocus());

        // Convert the result to an event when the done button is clicked.
        this.setResultConverter(dialogButton -> createTaskOnDoneClicked(dialogButton));
    }

    private Object createTaskOnDoneClicked(Object dialogButton) {
        if (dialogButton == doneButtonType) {
            Color c;
            try {
                Field field = Class.forName("java.awt.Color").getField(colourChoice.getValue());
                c = (Color)field.get(null);
            } catch (Exception e) {
                c = Color.GREEN; // Not chosen was not defined
            }

            if(title.getText().isEmpty()){
                return null;
            }

            int mod = 0;
            if (((RadioButton) groupAMPM.getSelectedToggle()).getText().equals("PM")){
                mod = 12;
            }
            LocalTime eTime = LocalTime.of(hour.getValue()+mod, minute.getValue());


            Task newTask =  new Task(title.getText(), desc.getText(), null, c,
                    datePicker.getValue().getDayOfMonth(), datePicker.getValue().getMonthValue(),
                    datePicker.getValue().getYear(), Time.valueOf(eTime));
                    //TODO: change the constructor to accept all fields
                    //datePicker.getValue().getDayOfMonth(), datePicker.getValue().getMonthValue(),
                    //datePicker.getValue().getYear(),  Time.valueOf(eTime));

            if (!courseChoice.getValue().equals("None")){
                newTask.setCourse(courseChoice.getValue());
            }

            return newTask;
        }
        return null;
    }
}
