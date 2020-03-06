package View;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Dashboard extends Pane implements PlannerListener {
    //Subview classes for the sidebar
    DaySidebar dayView;
    FullCalendarView calendarView;
    GradeSidebar gradeView;
    TaskSidebar taskView;


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
    Button addGradesbutton;
    Button addTasksbutton;
    Button addDaybutton;

    // Lists for the tabs
    ListView gradesList;
    ListView tasksList;
    ListView dayList;

    // Window set up values
    Rectangle2D bounds;


    public Dashboard(double width, double height, double minX, double minY) {

        /*setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        */


    }

    public Dashboard(Rectangle2D bounds, FullCalendarView calendarView, GradeSidebar gradeView, TaskSidebar taskView, DaySidebar dayView) {
        this.taskView = taskView;
        this.gradeView = gradeView;
        this.calendarView = calendarView;
        this.dayView = dayView;
        this.bounds = bounds;

        border = new BorderPane();
        tabPane = new TabPane();

        createTabComponents();
        createTabs();

        // Set the bounds of the calendar
        calendarBoundingBox = new VBox(calendarView.getView());
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

        this.getChildren().add(border);
    }

    public void createTabComponents(){

        //Initialize the component for the grades type

        // Initialize the list
        gradesList = new ListView();
        gradesList.setPrefWidth(100);
        gradesList.setPrefHeight(700);
        gradesList.fixedCellSizeProperty();

        // Initialize the button
        addGradesbutton = new Button("New Grade");
        addGradesbutton.setPrefHeight(60);
        addGradesbutton.setPrefWidth(100);

        // Assign the list views and the buttons to their appropriate
        gradesBox = new VBox(gradesList, addGradesbutton);
        gradesBox.setPrefSize(100, 800);
        gradesBox.setAlignment(Pos.CENTER_LEFT);


        // Initialize Components for the tasks tab
        tasksList = new ListView();
        tasksList.setPrefWidth(100);
        tasksList.setPrefHeight(700);
        tasksList.fixedCellSizeProperty();

        addTasksbutton = new Button("New Task");
        addTasksbutton.setPrefHeight(60);
        addTasksbutton.setPrefWidth(100);

        tasksBox = new VBox(tasksList, addTasksbutton);
        tasksBox.setPrefSize(100, 800);
        tasksBox.setAlignment(Pos.CENTER_LEFT);


        // Initialize Components for the day tab
        // Does List view initialization
        dayList = new ListView();
        dayList.setPrefWidth(100);
        dayList.setPrefHeight(700);
        dayList.fixedCellSizeProperty();

        addDaybutton = new Button("New Event");
        addDaybutton.setPrefHeight(60);
        addDaybutton.setPrefWidth(100);

        dayBox = new VBox(dayList, addDaybutton);
        dayBox.setPrefSize(100, 800);
        dayBox.setAlignment(Pos.CENTER_LEFT);
    }

    /*
        Create the tabs for the sidebar
     */
    public void createTabs () {

        grades = new Tab("Grades", new Label("Show all the grades available"));
        tasks = new Tab("Tasks", new Label("Show all tasks for the month"));
        day = new Tab("Today's Events", new Label("Show all day events, grades, courses"));

        grades = new Tab("Grades", gradesBox);
        tasks = new Tab("Tasks"  , tasksBox);
        day = new Tab("Today's Events" , dayBox);

        tabPane.getTabs().add(day);
        tabPane.getTabs().add(tasks);
        tabPane.getTabs().add(grades);
    }

    public void draw() {

    }

    public void modelChanged() {
        draw();
    }
}
