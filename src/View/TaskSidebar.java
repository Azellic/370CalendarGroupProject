package View;

import Model.TaskBoardModel;

public class TaskSidebar implements PlannerListener  {
    TaskBoardModel model;

    public void setModel(TaskBoardModel newModel) {
        model = newModel;
    }

    public void draw() {

    }

    public void modelChanged() {
        draw();
    }
}
