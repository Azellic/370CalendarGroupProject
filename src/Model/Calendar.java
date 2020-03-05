package Model;

import java.awt.*;
import View.PlannerListener;
import Model.DataBase;

import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.time.YearMonth;
import java.util.Date;

import java.awt.*;
import java.util.ArrayList;

public class Calendar {
   private ArrayList<PlannerListener> subscribers;
   private ArrayList<Day> days;
   private Day selectedDay;
   private int selectedMonth;
   private int currentDay;
   private int currentMonth;
   private int currentYear;
   private ArrayList<Event> currentDayEvents;
   private ArrayList<Event> currentMonthEvents;

   public Calendar(int currentYear, int currentMonth, int currentDay)
           throws ParseException, SQLException, ClassNotFoundException {
       this.currentYear = currentYear;
       this.currentMonth = currentMonth;
       this.currentDay = currentDay;
       setCurrentDayEvents();
       setCurrentMonthEvents();
   }

   public void setSelectedDay(){

   }

   public void changeMonthTo(int month, int year){

   }

   public void setCurrentDayEvents() throws ParseException, SQLException, ClassNotFoundException {
       this.currentDayEvents = Event.getDaysEvents(currentYear, currentMonth, currentDay);
   }
   public void setCurrentMonthEvents() throws ParseException, SQLException, ClassNotFoundException {
       this.currentMonthEvents = Event.getMonthsEvents(selectedMonth);
   }
   public ArrayList<Event> getCurrentDayEvents(){
       return currentDayEvents;
   }
   public ArrayList<Event> getCurrentMonthEvents(){
       return currentMonthEvents;
   }

   public void newEvent(String title, String description, Course course, Color color,
                        int day, int month, int year, Time start, Time end, String location ){
        Event event = new Event(title,description,course,color, day, month, year, start, end, location);
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
