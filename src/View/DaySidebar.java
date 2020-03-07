package View;

import Model.Calendar;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class DaySidebar extends VBox implements PlannerListener {
    Calendar model;
    VBox dayBox;
    Button addEventbutton;
    ListView dayList;

    public DaySidebar(Rectangle2D bounds) {

        // Initialize Components for the day tab
        // Does List view initialization
        dayList = new ListView();
        dayList.setPrefWidth(100);
        dayList.setPrefHeight(700);
        dayList.fixedCellSizeProperty();

        addEventbutton = new Button("New Event");
        addEventbutton.setMinHeight(60);
        addEventbutton.setPrefWidth(100);

        this.setPrefSize(100, bounds.getHeight());
        VBox buttonBar = new VBox(addEventbutton);
        buttonBar.setMaxHeight(65);
        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().add(dayList);
        this.getChildren().add(addEventbutton);
    }

    public void setModel(Calendar newModel) {
        model = newModel;
    }

    public void draw() {

    }

    public void modelChanged() {
        draw();
    }
}
