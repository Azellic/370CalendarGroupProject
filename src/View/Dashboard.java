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

    // Window set up values
    Rectangle2D bounds;

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

        border.setPrefHeight(bounds.getHeight());
        border.setPrefWidth(bounds.getWidth());

        this.setPrefHeight(bounds.getHeight());
        this.setPrefWidth(bounds.getWidth());
        this.getChildren().add(border);
    }

    public void createTabComponents(){

    }

    /*
        Create the tabs for the sidebar
     */
    public void createTabs () {

        grades = new Tab("Grades", new Label("Show all the grades available"));
        tasks = new Tab("Tasks", new Label("Show all tasks for the month"));
        day = new Tab("Today's Events", new Label("Show all day events, grades, courses"));

        grades = new Tab("Grades", gradeView);
        tasks = new Tab("Tasks"  , taskView);
        day = new Tab("Today's Events" , dayView);

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setTabMinWidth(bounds.getWidth()/10.5);
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
