package View;

import Model.Calendar;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CalendarView extends Pane implements PlannerListener{
    Calendar model;
    Canvas myCanvas;
    GraphicsContext gc;
    double width, height;

    public CalendarView(int newWidth, int newHeight) {
        width = newWidth;
        height = newHeight;
        setStyle("-fx-background-color: blueviolet");
        myCanvas = new Canvas(width, height);
        gc = myCanvas.getGraphicsContext2D();
        getChildren().add(myCanvas);
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
