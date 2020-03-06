import Controller.*;
import Model.Calendar;
import Model.CoursesModel;
import Model.DataBase;
import Model.TaskBoardModel;
import View.*;
import View.FullCalendarView;

import static javafx.application.Application.launch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.YearMonth;

// JavaFX packages we need
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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

    // The view the calendar is stored in
    FullCalendarView calendarv;
    BorderPane border;



    @Override
    public void start (Stage primaryStage)  throws Exception {
        StackPane root = new StackPane();
        border = new BorderPane();


        calController = new CalendarController();
        dashController = new DashboardController();
        daytabController = new DayTabController();
        gradeController = new GradeTabController();
        taskController = new TaskTabController();

        calendarModel = new Calendar();
        //System.out.println(calendarModel.getCurrentDayEvents());
        coursesModel = new CoursesModel();
        taskModel = new TaskBoardModel();

        //Set up the controllers with respective models
        calController.setModel(calendarModel);
        daytabController.setModel(calendarModel);
        gradeController.setModel(coursesModel);
        taskController.setModel(taskModel);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        //Screen screen = Screen.getPrimary();
        bounds = screen.getVisualBounds();
        //dashboard = new Dashboard(bounds);

        dayView = new DaySidebar();
        taskView = new TaskSidebar();
        calendarView = new FullCalendarView(YearMonth.now(), calController);
        gradeView = new GradeSidebar();

        dashboard = new Dashboard(bounds, calendarView, gradeView, taskView, dayView);

        //Set up each view with the model it will draw
        dayView.setModel(calendarModel);
        taskView.setModel(taskModel);
        calendarView.setModel(calendarModel);
        gradeView.setModel(coursesModel);

        dayView.setStage(primaryStage);

        /*
        createTabComponents();
        createTabs();
        */
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

        /*
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
        */
        // Set the two regions onto the main window
        /*
        border.setLeft(dashboard.getCalendarBox());
        border.setRight(dashboard.getSideBar());
        */

        // Set items in the border into the scene and display the scene
        root.getChildren().add(dashboard);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /*
        Create the tabs for the sidebar
     */
    /*
    public void createTabs () {

        grades = new Tab("Grades", new Label("Show all the grades available"));
        tasks = new Tab("Tasks", new Label("Show all tasks for the month"));
        day = new Tab("Today's Events", new Label("Show all day events, grades, courses"));

        grades = new Tab("Grades", gradesBox);
        tasks = new Tab("Tasks"  , tasksBox);
        day = new Tab("Today's Events" , dayBox);

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
    */

    // Added comment above main

    public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {
        DataBase db = new DataBase();
        db.startUp();
        ResultSet eventResult = db.displayEvents();
        int cDay = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);
        int cMonth = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1;
        int cYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        if (!eventResult.next()) {
            db.insertEvent(1,
                    "9:30",
                    "10:30",
                    cDay,
                    cMonth,
                    cYear,
                    "CMPT370 Project1",
                    "Write code for the project",
                    "STM College");
            db.insertEvent(1,
                    "9:30",
                    "11:30",
                    cDay,
                    cMonth,
                    cYear,
                    "CMPT370 Project2",
                    "Write code for the project",
                    "STM College");
            db.insertEvent(1,
                    "12:30",
                    "13:30",
                    cDay,
                    cMonth,
                    cYear,
                    "CMPT370 Project3",
                    "Write code for the project",
                    "STM College");
            db.insertEvent(1,
                    "10:30",
                    "11:30",
                    5,
                    2,
                    2020,
                    "CMPT370 Project",
                    "Write code for the project",
                    "STM College");
            db.insertEvent(1,
                    "9:30",
                    "10:30",
                    1,
                    2,
                    2020,
                    "CMPT370 Project",
                    "Write code for the project",
                    "STM College");
            db.insertEvent(1,
                    "9:30",
                    "10:30",
                    1,
                    2,
                    2020,
                    "CMPT370 Project",
                    "Write code for the project",
                    "STM College");
            db.insertEvent(1,
                    "9:30",
                    "10:30",
                    5,
                    4,
                    2020,
                    "CMPT370 Project",
                    "Write code for the project",
                    "STM College");
            db.insertEvent(1,
                    "9:30",
                    "10:30",
                    5,
                    4,
                    2020,
                    "CMPT370 Project",
                    "Write code for the project",
                    "STM College");
            db.insertEvent(1,
                    "9:30",
                    "10:30",
                    1,
                    4,
                    2020,
                    "CMPT370 Project",
                    "Write code for the project",
                    "STM College");
            db.insertEvent(1,
                    "9:30",
                    "10:30",
                    1,
                    5,
                    2020,
                    "CMPT370 Project",
                    "Write code for the project",
                    "STM College");
            db.insertEvent(1,
                    "9:30",
                    "10:30",
                    5,
                    5,
                    2020,
                    "CMPT370 Project",
                    "Write code for the project",
                    "STM College");
            db.insertEvent(1,
                    "9:30",
                    "10:30",
                    5,
                    5,
                    2020,
                    "CMPT370 Project",
                    "Write code for the project",
                    "STM College");
        }

        //DON'T PUT THINGS HERE. EVERYTHING SHOULD BE CREATED IN THE START FUNCTION
        launch(args);


    }
}
