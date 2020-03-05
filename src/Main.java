import Controller.*;
import Model.*;
import View.*;
import Calendar.FullCalendarView;

import static javafx.application.Application.launch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;

// JavaFX packages we need
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.TabPane;

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

    // Window set up values
    Rectangle2D bounds;

    // Bounding boxes for the window objects
    BorderPane border;
    VBox calendarBoundingBox; // To control the size of the calendar
    VBox calendarBox;
    VBox sideBar;

    // Parts of the tab pane
    TabPane tabPane;
    Tab grades;
    Tab tasks;
    Tab day;

    //
    FullCalendarView calendarv;

    @Override
    public void start(Stage primaryStage)  throws Exception{
        /*
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        */
        /*
        Code used for display the calendar
            primaryStage.setTitle("Full Calendar Example");
        primaryStage.setScene(new Scene(new FullCalendarView(YearMonth.now()).getView()));
        primaryStage.show();
         */
        calController = new CalendarController();

        calendarv = new FullCalendarView(YearMonth.now(), calController);
        tabPane = new TabPane();
        border = new BorderPane();


        int w = 600;
        int h = 800;

        calendarView = new CalendarView(w, h);

        createTabs();

        // Set the title
        primaryStage.setTitle("CMPT370 Project");

        Screen screen = Screen.getPrimary();
        bounds = screen.getVisualBounds();

        // Set the window size based on the screen bounds
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        // Set the bounds of the calendar
        calendarBoundingBox = new VBox(calendarv.getView());
        calendarBoundingBox.setMaxSize((bounds.getWidth()*2/3) - bounds.getWidth()*0.05, bounds.getHeight());
        calendarBoundingBox.setAlignment(Pos.CENTER);
        calendarBoundingBox.setStyle("-fx-background-color: lightgrey");

        // set the calender view to the window
        calendarBox = new VBox(calendarBoundingBox);
        calendarBox.setPrefSize(bounds.getWidth()*2/3, bounds.getHeight());
        calendarBox.setAlignment(Pos.CENTER);
        calendarBox.setStyle("-fx-background-color: dimgrey");

        // Set the buttons to the side bar
        //buttonBox = new HBox(tabPane);
        //buttonBox.setPrefSize(bounds.getWidth()/3, 40);
        //buttonBox.setAlignment(Pos.TOP_CENTER);

        // Set the the buttons on the side bar
        sideBar = new VBox(tabPane);
        sideBar.setPrefSize(bounds.getWidth()/3, bounds.getHeight());
        sideBar.setAlignment(Pos.TOP_CENTER);
        sideBar.setStyle("-fx-background-color: darkgrey");

        // Set the two regions onto the main window
        border.setLeft(calendarBox);
        border.setRight(sideBar);

        // Set items in the border into the scene and display the scene
        Scene scene = new Scene(border);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /*
        Create the tabs for the sidebar
     */
    public void createTabs() {

        grades = new Tab("Grades", new Label("Show all the grades available"));
        tasks = new Tab("Tasks"  , new Label("Show all tasks for the month"));
        day = new Tab("Day" , new Label("Show all day events, grades, courses"));

        tabPane.getTabs().add(grades);
        tabPane.getTabs().add(tasks);
        tabPane.getTabs().add(day);

    }

    // Added comment above main


    public static void main(String[] args) {
        launch(args);
    }
}
