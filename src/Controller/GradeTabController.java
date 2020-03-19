package Controller;

import Model.Assessment;
import Model.Course;
import Model.CoursesModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.util.Optional;

public class GradeTabController {
    CoursesModel model;

    public void setModel(CoursesModel newModel) {
        model = newModel;
    }

    public void handleAddGradeClicked(ActionEvent actionEvent) {
        Dialog<Assessment> dialog = createNewGradeDialog();

        Optional<Assessment> result = dialog.showAndWait();

        result.ifPresent(event -> {
            //TODO: send new assessment to model
        });
    }

    private Dialog<Assessment> createNewGradeDialog() {
        Dialog<Assessment> dialog = new Dialog<>();
        dialog.setTitle("Enter A Grade");

        ButtonType doneButtonType = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(doneButtonType, ButtonType.CANCEL);

        VBox addGradeBox = new VBox();
        addGradeBox.setPrefWidth(400);

        Label mainLabel = new Label("Enter new grade information");
        mainLabel.setFont(new Font("Arial", 16));
        TextField title = new TextField();
        title.setPromptText("Title");
        Tooltip.install(title, new Tooltip("Hitting done with an empty title is the same as hitting cancel"));
        DatePicker finishDate = new DatePicker(LocalDate.now());
        finishDate.setEditable(false);
        TextField mark = createDoubleInputField();
        mark.setPrefWidth(80);
        mark.setPromptText("Mark");
        TextField weight = createDoubleInputField();
        weight.setPromptText("Weight");
        weight.setPrefWidth(80);
        Tooltip.install(mark, new Tooltip("Not limited to 0-100. Go ahead, give 110%!"));
        ObservableList<String> courses = FXCollections.observableArrayList("None");
        //TODO: Generate the list of courses using course model
        ComboBox<String> courseChoice = new ComboBox<>(courses);
        courseChoice.setValue("None");
        TextField desc = new TextField();
        desc.setPromptText("Description");

        Region r = new Region();
        r.setPrefWidth(20);
        HBox markBox = new HBox(new Label("Mark (*):"),mark, r, new Label("Weight:"), weight);
        markBox.setAlignment(Pos.CENTER_LEFT);
        addGradeBox.getChildren().addAll(mainLabel, new Label("(Must enter all starred fields)"),
                new Label("Title (*):"), title, new Label("Course (*):"), courseChoice, markBox,
                new Label("Date Received:"), finishDate, new Label("Description:"), desc );

        dialog.getDialogPane().setContent(addGradeBox);
        Platform.runLater(() -> title.requestFocus());

        // Convert the result to an assessment when the done button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == doneButtonType) {
                if(title.getText().isEmpty() || mark.getText().isEmpty() || courseChoice.getValue().equals("None")){
                    return null;
                }

                //TODO: properly create new grade, using all input
                Assessment newGrade = new Assessment();

                return newGrade;
            }
            return null;
        });
        return dialog;
    }

    public void handleAddCourseClicked(ActionEvent actionEvent) {
        Dialog<Course> dialog = createNewCourseDialog();

        Optional<Course> result = dialog.showAndWait();

        result.ifPresent(event -> {
            //TODO: send new course to model
        });
    }

    private Dialog<Course> createNewCourseDialog() {
        Dialog<Course> dialog = new Dialog<>();
        dialog.setTitle("Create A Course");

        ButtonType doneButtonType = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(doneButtonType, ButtonType.CANCEL);

        VBox addCourseBox = new VBox();
        addCourseBox.setPrefWidth(400);

        Label mainLabel = new Label("Enter new grade information");
        mainLabel.setFont(new Font("Arial", 16));
        TextField title = new TextField();
        title.setPromptText("Title");
        Tooltip.install(title, new Tooltip("Hitting done with an empty title is the same as hitting cancel"));
        TextField prof = new TextField();
        prof.setPromptText("Instructor");
        TextField desc = new TextField();
        desc.setPromptText("Description");


        addCourseBox.getChildren().addAll(mainLabel, new Label("(Must enter all starred fields)"),
                new Label("Title (*):"), title, new Label("Instructor:"), prof,
                new Label("Description:"), desc);

        dialog.getDialogPane().setContent(addCourseBox);

        Platform.runLater(() -> title.requestFocus());

        // Convert the result to an assessment when the done button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == doneButtonType) {
                System.out.println("Created assessment");
                if(title.getText().isEmpty()){
                    return null;
                }

                //TODO: properly create new course, using all input
                Course newCourse = new Course(title.getText());

                return newCourse;
            }
            return null;
        });
        return dialog;
    }

    private TextField createDoubleInputField(){
        TextField doubleField = new TextField();
        doubleField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    doubleField.setText(oldValue);
                }
            }
        });
        return doubleField;
    }
}
