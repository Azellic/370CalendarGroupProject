package View;

import Model.TaskBoardModel;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class TaskSidebar extends VBox implements PlannerListener  {
    TaskBoardModel model;
    Button addTasksbutton;
    ListView tasksList;


    public TaskSidebar(Rectangle2D bounds) {
        // Initialize Components for the tasks tab
        tasksList = new ListView();
        tasksList.setPrefWidth(100);
        tasksList.setPrefHeight(700);
        tasksList.fixedCellSizeProperty();

        addTasksbutton = new Button("New Task");
        addTasksbutton.setPrefHeight(60);
        addTasksbutton.setPrefWidth(100);

        //this.setPrefWidth(100);
        //this.setPrefSize(100, bounds.getHeight());
        //this.setAlignment(Pos.CENTER_LEFT);
        this.setPrefSize(100,800);
        this.setAlignment(Pos.TOP_LEFT);
        this.getChildren().add(tasksList);
        this.getChildren().add(addTasksbutton);
    }

    public void setModel(TaskBoardModel newModel) {
        model = newModel;
    }

    public void draw() {

    }

    public void modelChanged() {
        draw();
    }
}
