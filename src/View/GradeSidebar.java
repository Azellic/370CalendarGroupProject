package View;

import Controller.GradeTabController;
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
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GradeSidebar extends VBox implements PlannerListener {

    private CoursesModel model;
    ListView gradesList;
    private Button addGradeButton, addCourseButton;
    ObservableList<String> courses;
    ComboBox<String> courseChoice;

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
    }

    public void setModel(CoursesModel newModel) {
        model = newModel;
    }

    public void draw() {

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
}
