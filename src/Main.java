import Controller.*;
import Model.Calendar;
//import Model.CalendarItem;
import Model.CoursesModel;
import Model.DataBase;
import Model.TaskBoardModel;
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
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;


// Main execution for the app

// test
//public class Main {//extends Application{

public class Main extends Application {
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

    // Boxes to add to the tabs
    VBox gradesBox;
    VBox tasksBox;
    VBox dayBox;

    // Buttons for adding new grades, tasks, and day
    Button addGradesb;
    Button addTasksb;
    Button addDayb;

    // The view the calendar is stored in
    FullCalendarView calendarv;

    // Lists for the tabs
    ListView gradesList;
    ListView tasksList;
    ListView dayList;



    @Override
    public void start (Stage primaryStage)  throws Exception {
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

        calendarv = new FullCalendarView(YearMonth.now());
        tabPane = new TabPane();
        border = new BorderPane();


        int w = 600;
        int h = 800;

        calendarView = new CalendarView(w, h);

        createTabComponents();
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
        calendarBoundingBox.setMaxSize((bounds.getWidth() * 2 / 3) - bounds.getWidth() * 0.05, bounds.getHeight());
        calendarBoundingBox.setAlignment(Pos.CENTER);
        calendarBoundingBox.setStyle("-fx-background-color: lightgrey");

        // set the calender view to the window
        calendarBox = new VBox(calendarBoundingBox);
        calendarBox.setPrefSize(bounds.getWidth() * 2 / 3, bounds.getHeight());
        calendarBox.setAlignment(Pos.CENTER);
        calendarBox.setStyle("-fx-background-color: dimgrey");

        // Set the buttons to the side bar
        //buttonBox = new HBox(tabPane);
        //buttonBox.setPrefSize(bounds.getWidth()/3, 40);
        //buttonBox.setAlignment(Pos.TOP_CENTER);

        // Set the the buttons on the side bar
        sideBar = new VBox(tabPane);
        sideBar.setPrefSize(bounds.getWidth() / 3, bounds.getHeight());
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
    public void createTabs () {

        grades = new Tab("Grades", new Label("Show all the grades available"));
        tasks = new Tab("Tasks", new Label("Show all tasks for the month"));
        day = new Tab("Day", new Label("Show all day events, grades, courses"));

        grades = new Tab("Grades", gradesBox);
        tasks = new Tab("Tasks"  , tasksBox);
        day = new Tab("Day" , dayBox);

        tabPane.getTabs().add(grades);
        tabPane.getTabs().add(tasks);
        tabPane.getTabs().add(day);

    }

    public void createTabComponents(){

        //Initialize the component for the grades type

        // Initialize the list
        gradesList = new ListView();
        gradesList.setPrefWidth(100);
        gradesList.setPrefHeight(700);
        gradesList.fixedCellSizeProperty();

        // Initialize the button
        addGradesb = new Button("New Grade");
        addGradesb.setPrefHeight(60);
        addGradesb.setPrefWidth(100);


        // Initialize Components for the tasks tab
        tasksList = new ListView();
        tasksList.setPrefWidth(100);
        tasksList.setPrefHeight(700);
        tasksList.fixedCellSizeProperty();

        addTasksb = new Button("New Task");
        addTasksb.setPrefHeight(60);
        addTasksb.setPrefWidth(100);


        // Initialize Components for the day tab
        // Does List view initialization
        dayList = new ListView();
        dayList.setPrefWidth(100);
        dayList.setPrefHeight(700);
        dayList.fixedCellSizeProperty();

        addDayb = new Button("New Event");
        addDayb.setPrefHeight(60);
        addDayb.setPrefWidth(100);

        // Assign the list views and the buttons to their appropriate
        gradesBox = new VBox(gradesList, addGradesb);
        gradesBox.setPrefSize(100, 800);
        gradesBox.setAlignment(Pos.CENTER_LEFT);

        tasksBox = new VBox(tasksList, addTasksb);
        tasksBox.setPrefSize(100, 800);
        tasksBox.setAlignment(Pos.CENTER_LEFT);

        dayBox = new VBox(dayList, addDayb);
        dayBox.setPrefSize(100, 800);
        dayBox.setAlignment(Pos.CENTER_LEFT);
    }
    // adding same comment for testing
    public static void main(String[] args) throws SQLException, ClassNotFoundException {


        DataBase test = new DataBase();
        test.startUp();



    // Added comment above main

        launch(args);


        // Added comment above main
    }
}
