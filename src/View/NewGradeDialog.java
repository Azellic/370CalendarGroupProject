package View;

import Model.Assessment;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDate;

public class NewGradeDialog extends InputDialog {
    ButtonType doneButtonType;
    VBox addGradeBox;
    TextField title, desc, mark, weight;
    DatePicker finishDate;


    public NewGradeDialog(ObservableList<String> courses) {
        this.setTitle("Enter A Grade");

        doneButtonType = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(doneButtonType, ButtonType.CANCEL);

        addGradeBox = new VBox();
        addGradeBox.setPrefWidth(400);

        Label mainLabel = new Label("Enter new grade information");
        mainLabel.setFont(new Font("Arial", 16));
        title = new TextField();
        title.setPromptText("Title");
        Tooltip.install(title, new Tooltip("Hitting done with an empty title is the same as hitting cancel"));
        finishDate = new DatePicker(LocalDate.now());
        finishDate.setEditable(false);
        mark = createDoubleInputField();
        mark.setPrefWidth(80);
        mark.setPromptText("Mark");
        weight = createDoubleInputField();
        weight.setPromptText("Weight");
        weight.setPrefWidth(80);
        Tooltip.install(mark, new Tooltip("Not limited to 0-100. Go ahead, give 110%!"));
        ComboBox<String> courseChoice = new ComboBox<>(courses);
        courseChoice.setValue("None");
        desc = new TextField();
        desc.setPromptText("Description");

        Region r = new Region();
        r.setPrefWidth(20);
        HBox markBox = new HBox(new Label("Mark (*):"),mark, r, new Label("Weight:"), weight);
        markBox.setAlignment(Pos.CENTER_LEFT);
        addGradeBox.getChildren().addAll(mainLabel, new Label("(Must enter all starred fields)"),
                new Label("Title (*):"), title, new Label("Course (*):"), courseChoice, markBox,
                new Label("Date Received:"), finishDate, new Label("Description:"), desc );

        this.getDialogPane().setContent(addGradeBox);
        Platform.runLater(() -> title.requestFocus());

        // Convert the result to an assessment when the done button is clicked.
        this.setResultConverter(dialogButton -> {
            if (dialogButton == doneButtonType) {
                if(title.getText().isEmpty() || mark.getText().isEmpty() || courseChoice.getValue().equals("None")){
                    return null;
                }

                //TODO: properly create new grade, using all input
              // Assessment newGrade = new Assessment();

                //return newGrade;
                return null;
            }
            return null;
        });
    }

 

}
