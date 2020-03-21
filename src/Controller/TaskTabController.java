package Controller;

import Model.CoursesModel;
import Model.Task;
import Model.Course;
import Model.TaskBoardModel;
import View.NewTaskDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.util.Optional;

public class TaskTabController {
    TaskBoardModel model;
    CoursesModel courseModel;
    //Course courseModel;

    public void setModel(TaskBoardModel newModel) {
        model = newModel;
    }

    public void setCoursesModel(CoursesModel newModel) {
        courseModel = newModel;
    }

    public void handleAddTaskClicked(ActionEvent actionEvent) {
        ObservableList<String> courses = FXCollections.observableArrayList("None");
        Dialog<Task> dialog = new NewTaskDialog(courses);  //TODO:pass list of courses to the dialog

        Optional<Task> result = dialog.showAndWait();

        result.ifPresent(event -> {
            //TODO: send new assessment to model
            System.out.println(event.toString());

            model.insertTask(event);
        });
    }
}
