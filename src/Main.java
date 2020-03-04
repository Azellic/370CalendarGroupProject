import Controller.*;
import Model.*;
import View.*;

import static javafx.application.Application.launch;

import java.sql.ResultSet;
import java.sql.SQLException;

// JavaFX packages we need
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

// Main execution for the app
public class Main extends Application{
    //Models
    Calendar calendarModel;
    CoursesModel coursesModel;
    TaskBoardModel taskModel;
    //Controllers
    CalendarController calController;
    DashboardController dashController;
    DayTabController daytabController;
    GradeTabController gradeController;
    TaskTabController taskController;
    //Views
    CalendarView calendarView;
    Dashboard dashboard;
    DaySidebar dayView;
    GradeSidebar gradeView;
    TaskSidebar taskView;

    Rectangle2D bounds;

    BorderPane border;
    VBox calendarBox;
    VBox sideBar;
    HBox buttonBox;

    Button grades;
    Button tasks;
    Button day;

    @Override
    public void start(Stage primaryStage)  throws Exception{
        /*
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        */
        border = new BorderPane();

        int w = 400;
        int h = 800;

        calendarView = new CalendarView(w, h);

        createButtons();
        primaryStage.setTitle("CMPT370 Project");

        Screen screen = Screen.getPrimary();
        bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        calendarBox = new VBox(calendarView);
        calendarBox.setPrefSize(bounds.getWidth()*2/3, bounds.getHeight());
        calendarBox.setAlignment(Pos.CENTER);
        
        buttonBox = new HBox(grades, tasks, day);
        buttonBox.setPrefSize(bounds.getWidth()/3, 40);
        buttonBox.setAlignment(Pos.TOP_CENTER);

        sideBar = new VBox(buttonBox);
        sideBar.setPrefSize(bounds.getWidth()/3, bounds.getHeight());
        sideBar.setAlignment(Pos.TOP_CENTER);

        border.setLeft(calendarBox);
        border.setRight(sideBar);

        Scene scene = new Scene(border);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /*
        Create the buttons for the window
     */
    public void createButtons() {
        grades = new Button("Grades");
        grades.setPrefHeight(40);
        grades.setPrefWidth(75);


        tasks = new Button("Tasks");
        tasks.setPrefHeight(40);
        tasks.setPrefWidth(75);

        day = new Button("Day");
        day.setPrefHeight(40);
        day.setPrefWidth(75);

    }

    // Added comment above main


    public static void main(String[] args) {
        launch(args);
    }
}
