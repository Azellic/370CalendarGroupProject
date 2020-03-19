package Model;

import java.awt.*;
import View.PlannerListener;
import Model.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.ArrayList;

public class Calendar {
   private ArrayList<PlannerListener> subscribers;
   private ArrayList<Day> days;
   //
   private int selectedDay;
   private int selectedMonth;
   private int selectedYear;
   //The current calendar day (today)
   private int currentDay;
   private int currentMonth;
   private int currentYear;
   private ArrayList<Event> currentDayEvents;
   private ArrayList<Event> currentMonthEvents;
   private ArrayList<Event> selectedMonthsEvents;

   public Calendar() throws ParseException, SQLException, ClassNotFoundException {
       subscribers = new ArrayList<>();
       int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
       int currentMonth = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1;
       int currentDay = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);
       this.currentYear = currentYear;
       this.currentMonth = currentMonth;
       this.currentDay = currentDay;
       this.selectedDay = currentDay;
       this.selectedMonth = currentMonth;
       this.selectedYear = currentYear;
       this.currentDayEvents = getDaysEvents();
       this.currentMonthEvents = getMonthsEvents();
   }

   public void setSelectedDay(LocalDate date){
       selectedDay = date.getDayOfMonth();
       selectedMonth = date.getMonthValue();
       selectedYear = date.getYear();
   }

   public void changeMonthBy(int increment) throws ParseException, SQLException, ClassNotFoundException {
       selectedMonth += increment;
       if(selectedMonth < 1){
           selectedMonth = 12;
           selectedYear -= 1;
       }
       else if(selectedMonth > 12){
            selectedMonth = 1;
            selectedYear += 1;
       }
       this.selectedMonthsEvents = getSelectedEvents();
       System.out.println(getSelectedMonthsEvents());
   }

    private static void formatEventQuery(ResultSet query, ArrayList<Event> events) throws SQLException, ParseException{
        while(query.next()){
            SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
            String startTimeString = query.getString("startTime");
            String endTimeString= query.getString("endTime");

            Time startTime = new Time(format1.parse(startTimeString).getTime());
            Time endTime = new Time(format1.parse(endTimeString).getTime());

            Event event = new Event(query.getString("eventTitle"),
                    query.getString("eventDescription"),
                    null,
                    null,
                    query.getInt("day"),
                    query.getInt("month"),
                    query.getInt("year"),
                    startTime,
                    endTime,
                    query.getString("eventLocation"));
            events.add(event);
        }
    }
    public void setSelectedMonthsEvents() throws SQLException, ClassNotFoundException, ParseException {
       this.selectedMonthsEvents = getSelectedEvents();
    }

   public ArrayList<Event> getDaysEvents() throws SQLException, ClassNotFoundException, ParseException {
       DataBase db = new DataBase();
       db.startUp();
       ResultSet eventsQuery = db.getDaysEvents(currentYear, currentMonth, currentDay);
       ArrayList<Event> events = new ArrayList<Event>();
       formatEventQuery(eventsQuery, events);
       return events;
   }
   public ArrayList<Event> getSelectedEvents() throws SQLException, ClassNotFoundException, ParseException {
       DataBase db = new DataBase();
       db.startUp();
       ResultSet eventsQuery = db.getSelectedEvents(selectedYear, selectedMonth);
       ArrayList<Event> events = new ArrayList<Event>();
       formatEventQuery(eventsQuery, events);
       return events;
   }
   public ArrayList<Event> getMonthsEvents() throws SQLException, ClassNotFoundException, ParseException {
       DataBase db = new DataBase();
       db.startUp();
       ResultSet eventsQuery = db.getMonthsEvents(currentMonth);
       ArrayList<Event> events = new ArrayList<Event>();
       formatEventQuery(eventsQuery, events);
       return events;
   }

   public ArrayList<Event> getCurrentDayEvents(){
       return currentDayEvents;
   }
   public ArrayList<Event> getCurrentMonthEvents(){
       return currentMonthEvents;
   }
   public ArrayList<Event> getSelectedMonthsEvents(){
       return selectedMonthsEvents;
   }

   public void insertEvent(Event userInput) throws SQLException,
           ClassNotFoundException {
       DataBase db = new DataBase();
       db.startUp();
       db.insertEvent(1, userInput.getStart().toString(),userInput.getEnd().toString(), userInput.getDay(),
               userInput.getMonth(), userInput.getYear(), userInput.getTitle(),userInput.getDescription(),
               userInput.getLocation(), userInput.getColor().toString());
       currentDayEvents.add(userInput);
       notifySubscribers();
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
