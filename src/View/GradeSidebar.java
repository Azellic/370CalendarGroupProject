package View;

import Controller.DayTabController;
import Model.CoursesModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class GradeSidebar extends VBox implements PlannerListener {

    private CoursesModel model;
    ListView gradesList;
    private Button addGradebutton;

    public GradeSidebar() {
        //Initialize the component for the grades type
        super();

        // Initialize the list
        gradesList = new ListView();
        gradesList.setPrefWidth(100);
        gradesList.setPrefHeight(700);
        gradesList.fixedCellSizeProperty();

        // Initialize the button
        addGradebutton = new Button("New Grade");
        addGradebutton.setPrefHeight(60);
        addGradebutton.setPrefWidth(100);

        this.setPrefSize(100,800);
        this.setAlignment(Pos.TOP_LEFT);
        this.getChildren().add(gradesList);
        this.getChildren().add(addGradebutton);
    }

    public void setModel(CoursesModel newModel) {
        model = newModel;
    }

    public void draw() {

    }

    public void modelChanged() {
        draw();
    }

    public void setButtonController(DayTabController controller) {
        addGradebutton.setOnAction(controller::handleAddEventClicked);
    }
}
