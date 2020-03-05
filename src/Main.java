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

    //
    FullCalendarView calendarv;

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

        tabPane.getTabs().add(grades);
        tabPane.getTabs().add(tasks);
        tabPane.getTabs().add(day);

    }
    // adding same comment for testing
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        DataBase test = new DataBase();
        test.startUp();

        launch(args);

        // Insert values careful not to add repeats on multiple runs
        ResultSet courseResult = test.displayCourses();
        if (!courseResult.next()) {
            test.insertCourse("CMPT370");
            test.insertCourse("CMPT340");
            test.insertCourse("MATH110");
            test.insertCourse("WORK");
        } else {
            System.out.println("\nTa Table Testing");
            System.out.println(courseResult.getInt("courseID") + " " + courseResult.getString("courseName"));
            while (courseResult.next()) {
                System.out.println(courseResult.getInt("courseID") + " " + courseResult.getString("courseName"));
            }
        }

        ResultSet eventResult = test.displayEvents();
        if (!eventResult.next()) {
            test.insertEvent(1, "10:30", "11:30",
                    3, 3, 2020, "Midterm");
            test.insertEvent(2, "11:30", "12:30",
                    27, 3, 2020, "Midterm");
            test.insertEvent(3, "7:30", "11:30",
                    7, 3, 2020, "Midterm");
            test.insertEvent(4, "10:30", "11:30",
                    3, 2, 2020, "Work Shift");
            test.insertEvent(1, "6:00", "11:30",
                    2, 6, 2020, "Class");
            test.insertEvent(2, "12:30", "2:45",
                    2, 5, 2020, "Class");
            test.insertEvent(3, "8:30", "11:30",
                    7, 8, 2020, "Class");
            test.insertEvent(4, "7:00", "10:00",
                    10, 3, 2020, "Work Shift");
        } else {
            System.out.println("\nEvent Table Tests");
            System.out.println(eventResult.getInt("eventID") + " " + eventResult.getInt("courseID") + " " +
                    eventResult.getString("startTime") + " " + eventResult.getString("endTime") + " " +
                    eventResult.getInt("day") + " " + eventResult.getInt("month") + " " +
                    eventResult.getInt("year") + " " + eventResult.getString("eventTitle"));
            while (eventResult.next()) {
                System.out.println(eventResult.getInt("eventID") + " " + eventResult.getInt("courseID") + " " +
                        eventResult.getString("startTime") + " " + eventResult.getString("endTime") + " " +
                        eventResult.getInt("day") + " " + eventResult.getInt("month") + " " +
                        eventResult.getInt("year") + " " + eventResult.getString("eventTitle"));
            }
        }
        ResultSet assessmentResult = test.displayAssessments();
        if (!assessmentResult.next()) {
            test.insertAssessment(1, 25, 100, "Midterm");
            test.insertAssessment(2, 50, 25, "Final");
            test.insertAssessment(3, 35, 37.5, "Final Project");
            test.insertAssessment(1, 5, 55.5, "assignment 1");
            test.insertAssessment(2, 10, 65, "Assignment 3");
        } else {
            System.out.println("\nAssessment Table Tests");
            System.out.println(assessmentResult.getInt("assessmentId") + " " + assessmentResult.getInt("courseID") + " " +
                    assessmentResult.getInt("grade") + " " + assessmentResult.getString("assessmentTitle"));
            while (assessmentResult.next()) {
                System.out.println(assessmentResult.getInt("assessmentId") + " " + assessmentResult.getInt("courseID") + " " +
                        assessmentResult.getInt("grade") + " " + assessmentResult.getString("assessmentTitle"));
            }
        }
        ResultSet tasksResult = test.displayTasks();
        if (!tasksResult.next()) {
            test.insertTask(1, 6, 4, 2020, "Assignment");
            test.insertTask(1, 5, 8, 2020, "Assignment");
            test.insertTask(2, 3, 4, 2020, "clean room");
            test.insertTask(3, 5, 6, 2020, "Do Dishes");
        } else {
            System.out.println("\nTasks Table Tests");
            System.out.println(tasksResult.getInt("taskID") + " " + tasksResult.getInt("courseID") + " " +
                    tasksResult.getInt("dueDay") + " " + tasksResult.getInt("dueMonth") + " " +
                    tasksResult.getInt("dueYear") + " " + tasksResult.getString("taskTitle"));
            while (tasksResult.next()) {
                System.out.println(tasksResult.getInt("taskID") + " " + tasksResult.getInt("courseID") + " " +
                        tasksResult.getInt("dueDay") + " " + tasksResult.getInt("dueMonth") + " " +
                        tasksResult.getInt("dueYear") + " " + tasksResult.getString("taskTitle"));
            }
        }

        // Added comment above main
    }
}
