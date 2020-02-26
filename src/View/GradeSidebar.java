package View;

import Model.CoursesModel;

public class GradeSidebar implements PlannerListener {
    CoursesModel model;

    public void setModel(CoursesModel newModel) {
        model = newModel;
    }

    public void draw() {

    }

    public void modelChanged() {
        draw();
    }
}
