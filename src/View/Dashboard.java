package View;

import Model.Calendar;

public class Dashboard implements PlannerListener {

    public void draw() {

    }

    public void modelChanged() {
        draw();
    }
}
