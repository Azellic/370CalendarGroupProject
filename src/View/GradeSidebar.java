package View;

import Controller.GradeTabController;
import Model.CoursesModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import Model.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class GradeSidebar extends VBox implements PlannerListener {

    private CoursesModel model;
    ListView gradesList;
    private Button addGradeButton, addCourseButton;
    ObservableList<String> courses;
    ComboBox<String> courseChoice;

    ObservableList<HBox> gradesListArray;

    public GradeSidebar(Rectangle2D bounds) {
        //Initialize the component for the grades type
        super();

        //Initialize and fill list of courses
        courses = FXCollections.observableArrayList("None");
        //TODO: Generate the list of courses using course model
        courseChoice = new ComboBox<>(courses);
        courseChoice.setValue("None");
        courseChoice.setPrefWidth(700);

        // Initialize the list
        gradesList = new ListView();
        gradesList.setPrefWidth(100);
        gradesList.setPrefHeight(705);
        gradesList.fixedCellSizeProperty();

        //Initialize the course button
        addCourseButton = new Button("New Course");
        addCourseButton.setPrefHeight(60);
        addCourseButton.setPrefWidth(100);


        // Initialize the grade button
        addGradeButton = new Button("New Grade");
        addGradeButton.setPrefHeight(60);
        addGradeButton.setPrefWidth(100);

        HBox buttonBar = new HBox(addGradeButton, addCourseButton);
        buttonBar.setPrefHeight(100);

        this.setPadding(new Insets(2,5,5,2));
        this.setPrefSize(100, bounds.getHeight());
        //this.setPrefSize(100,800);
        this.setAlignment(Pos.TOP_LEFT);
        this.getChildren().addAll(courseChoice, gradesList, buttonBar);

        // Draw will draw the sidebar with all the grades
        draw();
    }

    public void setModel(CoursesModel newModel) {
        model = newModel;
    }

    public void draw() {
        generateGradesList();
    }

    public void modelChanged() {
        draw();
    }

    public void setButtonController(GradeTabController controller) {
        addGradeButton.setOnAction(controller::handleAddGradeClicked);
        addCourseButton.setOnAction(controller::handleAddCourseClicked);
        courseChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if(!oldValue.equals(newValue)){
                    //TODO: tell the model that a new course has been selected, so that the view can draw the change
                    //model.setSelectedCourse(newValue);
                }
            }
        });
    }

    private void generateGradesList(){
        // TODO: Get the courses and grades from the model to generate the list of courses
        //ArrayList<Event> events = model.getGrades();
        gradesListArray = FXCollections.observableArrayList ();

        // TODO: change to the number of grades to generate when those vcan be retrieved
        // Change to: Event currentEvent :events
        for(int i=0; i < 1; i++) {
            /*
                Generate the elements to be added to to the sidebar that will be viewed
             */
            // Main title of the grade
            Label title = new Label("Grade Title");

            // TODO: Get the values of the grade and the total marks
            Label value = new Label("Grade: " + "Get the value / Total Marks");

            // The box that will be added to the
            HBox box = new HBox();

            VBox gradeDisplayInfo = new VBox();
            gradeDisplayInfo.setPadding(new Insets(2,2,2,2));
            gradeDisplayInfo.setPrefSize(500, 50);

            gradeDisplayInfo.getChildren().addAll(title, value);

            // Generates alternating colours for the boxes
            if (i % 2 == 0) {
                gradeDisplayInfo.setStyle("-fx-background-color: lightseagreen");
            } else {
                gradeDisplayInfo.setStyle("-fx-background-color: lightslategrey");
            }

            // Add the new box to the array to display
            box.getChildren().add(gradeDisplayInfo);

            gradesListArray.add(box);
        }

        gradesList.setItems(gradesListArray);
    }
}
