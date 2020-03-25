package View;

import Controller.GradeTabController;
import Model.Assessment;
import Model.Course;
import Model.CoursesModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    ObservableList<HBox> assessmentsListArray;
    private Button addGradeButton, addCourseButton;
    ObservableList<String> courses;
    ComboBox<String> courseChoice;

    public GradeSidebar(Rectangle2D bounds, CoursesModel mdl) {
        //Initialize the component for the grades type
        super();
        model = mdl;
        //Initialize and fill list of courses
        courseChoice = new ComboBox<>();
        courseChoice.setValue("None");
        populateCoursesList();
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

    public void draw() {
        generateGradesList();
        populateCoursesList();
    }

    public void modelChanged() {
        System.out.println("Model changed");
        draw();
    }

    public void setButtonController(GradeTabController controller) {
        addGradeButton.setOnAction(controller::handleAddGradeClicked);
        addCourseButton.setOnAction(controller::handleAddCourseClicked);
        courseChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if(oldValue!= null && newValue != null && !oldValue.equals(newValue)){
                    model.setSelectedCourse(newValue);
                }
            }
        });
    }

    private void populateCoursesList(){
        String currentChoice;
        ArrayList<String> courseStrings = new ArrayList<>();
        ArrayList<Course> allCourses = model.getCourseList();
        courseStrings.add("None");
        for(Course c : allCourses){
            courseStrings.add(c.getTitle());
        }
        System.out.println(courseStrings.toString());
        courses = FXCollections.observableArrayList(courseStrings);
        if(courseChoice != null){
            currentChoice = courseChoice.getValue();
            courseChoice.getItems().clear();
            courseChoice.getItems().addAll(courses);
            courseChoice.setValue(currentChoice);
        }
    }

    private void generateGradesList(){
        // TODO: Get the courses and grades from the model to generate the list of courses
        ArrayList<Assessment> assessments = model.getAssessmentList();
        System.out.println(assessments);
        if (assessments == null) {
            return;
        }
        assessmentsListArray = FXCollections.observableArrayList();

        // TODO: change to the number of grades to generate when those vcan be retrieved
        // Change to: Event currentEvent :events
        int i = 0;
        for(Assessment currentAssessment : assessments) {
            /*
                Generate the elements to be added to to the sidebar that will be viewed
             */
            // Main title of the grade
            //Label title = new Label("Grade Title");
            Label title = new Label(currentAssessment.getTitle());

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

            assessmentsListArray.add(box);
            i++;
        }

        gradesList.setItems(assessmentsListArray);
    }
}
