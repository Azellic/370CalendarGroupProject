package View;

import Model.CoursesModel;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class GradeSidebar extends VBox implements PlannerListener {

    CoursesModel model;
    VBox gradesBox;
    ListView gradesList;
    Button addGradesbutton;

    public GradeSidebar(Rectangle2D bounds) {
        //Initialize the component for the grades type
        super();

        // Initialize the list
        gradesList = new ListView();
        gradesList.setPrefWidth(100);
        gradesList.setPrefHeight(700);
        gradesList.fixedCellSizeProperty();

        // Initialize the button
        addGradesbutton = new Button("New Grade");
        addGradesbutton.setPrefHeight(60);
        addGradesbutton.setPrefWidth(100);

<<<<<<< HEAD
        this.setPrefSize(100, bounds.getHeight());
        this.setAlignment(Pos.CENTER_LEFT);
=======
        // Assign the list views and the buttons to their appropriate
        //gradesBox = new VBox(gradesList, addGradesbutton);
        //gradesBox.setPrefSize(100, 800);
        //gradesBox.setAlignment(Pos.TOP_LEFT);

        this.setPrefSize(100,800);
        this.setAlignment(Pos.TOP_LEFT);
>>>>>>> 434630e2df8202fe5a960533d287f3016e708726
        this.getChildren().add(gradesList);
        this.getChildren().add(addGradesbutton);
    }

    public void setModel(CoursesModel newModel) {
        model = newModel;
    }

    public void draw() {
        //TODO
    }

    public void modelChanged() {
        draw();
    }
}
