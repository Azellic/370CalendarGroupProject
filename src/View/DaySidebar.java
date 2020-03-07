package View;

import Controller.DayTabController;
import Model.Calendar;
import Model.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DaySidebar extends VBox implements PlannerListener {
    Calendar model;
    VBox dayBox;
    Button addEventbutton;
    ListView dayList;
    ObservableList<HBox> dayListArray;

    Stage primaryStage;

    public DaySidebar(){

        // Initialize Components for the day tab
        // Does List view initialization
        dayList = new ListView<VBox>();
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
        this.setAlignment(Pos.TOP_LEFT);
        this.getChildren().add(dayList);
        this.getChildren().add(addEventbutton);
    }

    public void setModel(Calendar newModel) {
        model = newModel;
        populateList();
    }

    public void draw() {
        populateList();
    }

    public void modelChanged() {
        draw();
    }


    public void populateList() {

        ArrayList<Event> events = model.getCurrentDayEvents();
        dayListArray = FXCollections.observableArrayList ();

        int i = 0;
        for(Event e :events){

            Label title = new Label(e.getTitle());
            Label time = new Label("Time: " + e.getStart() + " - " + e.getEnd());
            Label location = new Label("Location: " + e.getLocation());

            Button button = new Button("Details");
            button.setPrefSize(60,40);


            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent event) {
                    final Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(primaryStage);

                    VBox top = new VBox();
                    top.setPrefSize(800, 300);

                    VBox bottom = new VBox();

                    Label date = new Label("Date: " + e.getDay() + "/" + e.getMonth() + "/" + e.getYear());
                    date.setFont(new Font("Ariel", 20));

                    Label title = new Label(e.getTitle());
                    title.setFont(new Font("Ariel", 20));

                    Label time = new Label("Time: " + e.getStart() + " - " + e.getEnd());
                    time.setFont(new Font("Ariel", 15));

                    Label location = new Label("Location: " + e.getLocation());
                    location.setFont(new Font("Ariel", 15));

                    Label description = new Label(e.getDescription());

                    top.getChildren().addAll(title, date, time, location);
                    bottom.getChildren().add(description);

                    VBox dialogVbox = new VBox();
                    dialogVbox.setPrefSize(800, 800);
                    dialogVbox.getChildren().addAll(top, bottom);

                    Scene dialogScene = new Scene(dialogVbox, 300, 200);
                    dialog.setScene(dialogScene);
                    dialog.show();
                }
            });


            HBox box = new HBox();

            VBox left = new VBox(title, time, location);
            left.setPrefSize(200, 50);
            left.setAlignment(Pos.CENTER_LEFT);

            VBox right = new VBox(button);
            right.setPrefSize(200, 50);
            right.setAlignment(Pos.CENTER_RIGHT);

            box.getChildren().addAll(left, right);

            box.setAlignment(Pos.CENTER_LEFT);
            box.setPrefSize(400, 50);
            if(i%2 == 0){
                box.setStyle("-fx-background-color: lightseagreen");
            }
            else {
                box.setStyle("-fx-background-color: lightslategrey");
            }


            dayListArray.add(box);
            i++;
        }

        dayList.setItems(dayListArray);

    }

    public void setStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void setButtonController(DayTabController controller) {
        addEventbutton.setOnAction(controller::handleAddEventClicked);
    }
}
