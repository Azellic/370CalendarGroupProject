package Controller;

import Model.Assessment;
import Model.Course;
import Model.CoursesModel;
import View.NewCourseDialog;
import View.NewGradeDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import java.util.Optional;

public class GradeTabController {
    CoursesModel model;

    public void setModel(CoursesModel newModel) {
        model = newModel;
    }



    public void handleAddGradeClicked(ActionEvent actionEvent) {
        ObservableList<String> courses = FXCollections.observableArrayList("None");
        //TODO: Generate the list of courses using course model
        Dialog<Assessment> dialog = new NewGradeDialog(courses);

        Optional<Assessment> result = dialog.showAndWait();

        result.ifPresent(event -> {
            //TODO: send new assessment to model
        });
    }



    public void handleAddCourseClicked(ActionEvent actionEvent) {
        Dialog<Course> dialog = new NewCourseDialog();

        Optional<Course> result = dialog.showAndWait();

        result.ifPresent(event -> {
            //TODO: send new course to model
            System.out.println(event.toString());

            model.insertCourse(event);
        });
    }
}
