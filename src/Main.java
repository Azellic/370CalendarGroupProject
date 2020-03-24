import Controller.*;
import Model.*;
import View.*;
import View.FullCalendarView;

import static javafx.application.Application.launch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.YearMonth;
import java.awt.Color;

// JavaFX packages we need
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

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
    Dashboard dashboard;
    DaySidebar dayView;
    GradeSidebar gradeView;
    TaskSidebar taskView;
    FullCalendarView calendarView;

    // The view the calendar is stored in
    BorderPane border;

    public static DataBase db;

    @Override
    public void start (Stage primaryStage){
        StackPane root = new StackPane();
        border = new BorderPane();


        calController = new CalendarController();
        dashController = new DashboardController();
        daytabController = new DayTabController();
        gradeController = new GradeTabController();
        taskController = new TaskTabController();

        calendarModel = new Calendar();
        coursesModel = new CoursesModel();
        taskModel = new TaskBoardModel();

        //Set up the controllers with respective models
        calController.setModel(calendarModel);
        daytabController.setModel(calendarModel);
        daytabController.setCoursesModel(coursesModel);
        gradeController.setModel(coursesModel);
        taskController.setModel(taskModel);
        taskController.setCoursesModel(coursesModel);

        Screen screen = Screen.getPrimary();
        Rectangle2D wBounds = screen.getVisualBounds();
        Rectangle2D bounds = new Rectangle2D(wBounds.getMinX(), wBounds.getMinY(), wBounds.getWidth(),
                wBounds.getHeight()-20);

        dayView = new DaySidebar(bounds);
        taskView = new TaskSidebar(bounds);

        calendarView = new FullCalendarView(YearMonth.now(), calController);
        gradeView = new GradeSidebar(bounds);

        dashboard = new Dashboard(bounds, calendarView, gradeView, taskView, dayView);

        //Set up each view with the model it will draw
        dayView.setModel(calendarModel);
        taskView.setModel(taskModel);
        calendarView.setModel(calendarModel);
        gradeView.setModel(coursesModel);

        dayView.setStage(primaryStage);
        taskView.setStage(primaryStage);

        //Set up model-view subscriber relationship
        calendarModel.addSubscriber(dayView);
        calendarModel.addSubscriber(calendarView);
        taskModel.addSubscriber(taskView);
        coursesModel.addSubscriber(gradeView);

        //Establish controller - add-button relationship
        dayView.setButtonController(daytabController);
        gradeView.setButtonController(gradeController);
        taskView.setButtonController(taskController);


        // Set the title
        primaryStage.setTitle("CMPT370 Project");


        // Set the window size based on the screen bounds
        primaryStage.setX(wBounds.getMinX());
        primaryStage.setY(wBounds.getMinY());
        primaryStage.setWidth(wBounds.getWidth());
        primaryStage.setHeight(wBounds.getHeight());

        // Set items in the border into the scene and display the scene
        root.getChildren().add(dashboard);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {
        db = new DataBase();
        db.startUp();
        ResultSet eventResult = db.getAllEvents();
        int cDay = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);
        int cMonth = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1;
        int cYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        Color color = new Color(0.0f,0.0f,1.0f);
        if (!eventResult.next()) {
            db.insertEvent(1,
                    "9:30",
                    "10:30",
                    cDay,
                    cMonth,
                    cYear,
                    0,
                    0,
                    255,
                    "CMPT370 Project1",
                    "Write code for the project",
                    "STM College");
        }
        db.closeConnection();
        //DON'T PUT THINGS HERE. EVERYTHING SHOULD BE CREATED IN THE START FUNCTION
        launch(args);


    }
}
