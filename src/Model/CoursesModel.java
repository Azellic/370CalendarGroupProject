package Model;

import View.PlannerListener;

import java.util.ArrayList;

public class CoursesModel {
    ArrayList<Course> courses;
    ArrayList<PlannerListener> subscribers;

    public CoursesModel() {
        subscribers = new ArrayList<>();
    }

    public void addSubscriber (PlannerListener aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }
}
