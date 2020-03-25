package View;

import Controller.TaskTabController;
import Model.Event;
import Model.Task;
import Model.TaskBoardModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

public class TaskSidebar extends VBox implements PlannerListener  {

    TaskBoardModel model;
    Button addTaskbutton;
    ListView tasksList;
    ObservableList<HBox> taskListArray;

    Stage primaryStage;


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
        draw();
    }

    public void draw() {
        populateList();
    }

    public void modelChanged() {
        draw();
    }

    public void setButtonController(TaskTabController controller) {
        addTaskbutton.setOnAction(controller::handleAddTaskClicked);
    }

    public void setStage(Stage stage){
        primaryStage = stage;
    }

    private void populateList() {
        ArrayList<Task> tasks = model.getTasksFromDB();
        taskListArray = FXCollections.observableArrayList ();
        
        int i = 0;
        for(Task currentTask :tasks){
            
            Label title = new Label(currentTask.getTitle());
            Label day = new Label("Day: " + currentTask.getDay() + "/" + currentTask.getMonth() + "/" + currentTask.getYear());
            Label dueDate = new Label("Due Time: " + currentTask.getDueTime());

            Button button = new Button("Details");
            button.setPrefSize(80,40);

            // Moved details window code to its own function to fully view the event
            initializeDetailsButton(currentTask, button);

            HBox box = new HBox();
            box.setPadding(new Insets(2,2,2,2));

            VBox left = new VBox(title, day, dueDate);
            left.setPrefSize(200, 50);
            left.setAlignment(Pos.CENTER_LEFT);

            VBox right = new VBox(button);
            right.setPrefSize(200, 50);
            right.setAlignment(Pos.CENTER_RIGHT);

            box.getChildren().addAll(left, right);

            box.setAlignment(Pos.CENTER_LEFT);
            box.setPrefSize(400, 50);

           if(currentTask.getColor() == null) {
                if (i % 2 == 0) {
                    box.setStyle("-fx-background-color: lightseagreen");
                } else {
                    box.setStyle("-fx-background-color: lightslategrey");
                }
            }
            
            else {
                String colour = "-fx-background-color: " + getColour(currentTask.getColor());

                System.out.println("Colour found: " + colour);
                box.setStyle(colour);
            }
            taskListArray.add(box);
            i++;
        }
        tasksList.setItems(taskListArray);
    }

    /*
       Get the colour of the event and turn it into a string to use for colouring the event box
    */
    public String getColour(Color eventColour){
        String colour = "";

        System.out.println("Colour given: " + eventColour);
        if (Color.GREEN.equals(eventColour)) {
            colour = "green";
        }
        else if (Color.BLUE.equals(eventColour)) {
            colour = "blue";
        }
        else if (Color.RED.equals(eventColour)) {
            colour = "red";
        }
        else if (Color.ORANGE.equals(eventColour)) {
            colour = "orange";
        }
        else if (Color.YELLOW.equals(eventColour)) {
            colour = "yellow";
        }
        else {
            colour = "violet";  //default if not set to any of the of the acceptable colours
        }

        return colour;
    }

    /*
       Initalize functionality for the "Details" button on the event tab
       @param: currentEvent (The event the detaisl are added from), button (the button that will pop up the window)
    */
    public void initializeDetailsButton(Task currentTask, Button button){
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(primaryStage);
                dialog.setTitle(currentTask.getTitle() + " Details");

                VBox top = new VBox();
                top.setPrefSize(800, 300);
                top.setPadding(new Insets(5, 5, 5, 5));
                top.setSpacing(2);

                VBox descriptionBox = new VBox();
                descriptionBox.setPrefSize(800, 300);
                descriptionBox.setStyle("-fx-border-color: grey;\n" +
                        "-fx-border-insets: 2;\n" +
                        "-fx-border-width: 2;\n" +
                        "-fx-border-style: solid inside;\n" +
                        "-fx-background-color: lightgrey;\n");
                Label date = new Label("Date: " + currentTask.getDay() + "/" + currentTask.getMonth() + "/" + currentTask.getYear());
                date.setFont(new Font("Arial", 15));

                Label title = new Label(currentTask.getTitle());
                title.setFont(new Font("Ariel", 16));

                Label course = new Label("Course: " + currentTask.getCourseName());
                course.setFont(new Font("Arial", 15));

                Label day = new Label("Day: " + currentTask.getDay() + "/" + currentTask.getMonth() + "/" + currentTask.getYear());
                day.setFont(new Font("Ariel", 15));

                Label dueDate = new Label("Due Time: " + currentTask.getDueTime());
                dueDate.setFont(new Font("Ariel", 15));

                Label description = new Label(currentTask.getDescription());
                description.setWrapText(true);

                top.getChildren().addAll(title, course, date, day, dueDate);
                descriptionBox.getChildren().add(description);

                VBox dialogVbox = new VBox();
                dialogVbox.setPrefSize(800, 800);
                dialogVbox.getChildren().addAll(top, descriptionBox);

                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                dialog.setScene(dialogScene);
                dialog.show();
            }
        });
    }
}
