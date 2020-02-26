package Model;

import View.PlannerListener;

import java.util.ArrayList;

public class TaskBoardModel {
    ArrayList<PlannerListener> subscribers;

    public void addSubscriber (PlannerListener aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }
}
