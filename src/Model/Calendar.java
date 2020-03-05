package Model;

import java.awt.*;
import View.PlannerListener;
import Model.DataBase;

import java.awt.*;
import java.util.ArrayList;

public class Calendar {
    ArrayList<PlannerListener> subscribers;
    ArrayList<Day> days;
    Day selectedDay;

    public void setSelectedDay(){

    }

    public void changeMonthTo(int month, int year){

    }

    public void newEvent(String title, String description, Course course){
        Event event = new Event(title,description,course);
        addEventToDB(event);
        addEventToCache(event);
        notifySubscribers();

    }

    public void addSubscriber (PlannerListener aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }

    private void addEventToCache(Event event){

    }

    private void addEventToDB(Event event){

    }


}
