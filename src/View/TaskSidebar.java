package View;

import Controller.TaskTabController;
import Model.TaskBoardModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class TaskSidebar extends VBox implements PlannerListener  {
    TaskBoardModel model;
    Button addTaskbutton;
    ListView tasksList;


    public TaskSidebar(Rectangle2D bounds) {
        // Initialize Components for the tasks tab
        tasksList = new ListView();
        tasksList.setPrefWidth(100);
        tasksList.setPrefHeight(700);
        tasksList.fixedCellSizeProperty();

        addTaskbutton = new Button("New Task");
        addTaskbutton.setPrefHeight(60);
        addTaskbutton.setPrefWidth(100);

        this.setPadding(new Insets(2,5,5,2));
        this.setPrefSize(100,800);
        this.setAlignment(Pos.TOP_LEFT);
        this.getChildren().add(tasksList);
        this.getChildren().add(addTaskbutton);
    }

    public void setModel(TaskBoardModel newModel) {
        model = newModel;
    }

    public void draw() {

    }

    public void modelChanged() {
        draw();
    }

    public void setButtonController(TaskTabController controller) {
        addTaskbutton.setOnAction(controller::handleAddTaskClicked);
    }
}
