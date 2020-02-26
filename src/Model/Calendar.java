package Model;

import View.PlannerListener;

import java.util.ArrayList;

public class Calendar {
    ArrayList<PlannerListener> subscribers;
    ArrayList<Day> days;
    Day selectedDay;

    public void setSelectedDay(){

    }

    public void addSubscriber (PlannerListener aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }
}
