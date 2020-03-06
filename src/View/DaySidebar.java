package View;

import Controller.DayTabController;
import Model.Calendar;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class DaySidebar extends VBox implements PlannerListener {
    Calendar model;
    VBox dayBox;
    Button addEventbutton;
    ListView dayList;

    public DaySidebar() {

        // Initialize Components for the day tab
        // Does List view initialization
        dayList = new ListView();
        dayList.setPrefWidth(100);
        dayList.setPrefHeight(700);
        dayList.fixedCellSizeProperty();

        addEventbutton = new Button("New Event");
        addEventbutton.setPrefHeight(60);
        addEventbutton.setPrefWidth(100);

        dayBox = new VBox(dayList, addEventbutton);
        dayBox.setPrefSize(100, 800);
        dayBox.setAlignment(Pos.CENTER_LEFT);

        this.setPrefSize(100,800);
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

    public void setButtonController(DayTabController controller) {
        addEventbutton.setOnAction(controller::handleAddEventClicked);
    }
}
