package View;

import Model.Calendar;

public class DaySidebar implements PlannerListener {
    Calendar model;

    public void setModel(Calendar newModel) {
        model = newModel;
    }

    public void draw() {

    }

    public void modelChanged() {
        draw();
    }
}
