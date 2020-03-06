import Controller.*;
import Model.Calendar;
import Model.CoursesModel;
import Model.DataBase;
import Model.TaskBoardModel;
import View.*;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.YearMonth;


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




    @Override
    public void start (Stage primaryStage)  throws Exception {
        StackPane root = new StackPane();

        calController = new CalendarController();
        dashController = new DashboardController();
        daytabController = new DayTabController();
        gradeController = new GradeTabController();
        taskController = new TaskTabController();

        calendarModel = new Calendar();
        System.out.println(calendarModel.getCurrentDayEvents());
        coursesModel = new CoursesModel();
        taskModel = new TaskBoardModel();

        //Set up the controllers with respective models
        calController.setModel(calendarModel);
        daytabController.setModel(calendarModel);
        gradeController.setModel(coursesModel);
        taskController.setModel(taskModel);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

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

        //Set up model-view subscriber relationship
        calendarModel.addSubscriber(dayView);
        calendarModel.addSubscriber(calendarView);
        taskModel.addSubscriber(taskView);
        coursesModel.addSubscriber(gradeView);

        // Set the title
        primaryStage.setTitle("CMPT370 Project");


        root.setPrefWidth(bounds.getWidth());
        root.setPrefHeight(bounds.getHeight());

        // Set the window size based on the screen bounds
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        // Set items in the border into the scene and display the scene
        root.getChildren().add(dashboard);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {
        DataBase db = new DataBase();
        db.startUp();
        //DON'T PUT THINGS HERE. EVERYTHING SHOULD BE CREATED IN THE START FUNCTION
        launch(args);

    }
}
