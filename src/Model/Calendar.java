package Model;

import java.awt.*;
import View.PlannerListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class Calendar {
   private ArrayList<PlannerListener> subscribers;
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
   private DataBase db;

   public Calendar() {
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
       this.db = new DataBase();
       this.currentDayEvents = getDaysEvents();
       this.currentMonthEvents = getMonthsEvents();
   }

   public void setSelectedDay(LocalDate date){
       selectedDay = date.getDayOfMonth();
       selectedMonth = date.getMonthValue();
       selectedYear = date.getYear();
   }

   public void changeMonthBy(int increment) {
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


   private static void formatEventQuery(ResultSet query, ArrayList<Event> events) {
       try {
           while (query.next()) {
               SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
               String startTimeString = query.getString("startTime");
               String endTimeString = query.getString("endTime");

               Time startTime = new Time(format1.parse(startTimeString).getTime());
               Time endTime = new Time(format1.parse(endTimeString).getTime());

               Color eventColor = new Color(query.getInt("colorRedInt"),
                                            query.getInt("colorGreenInt"),
                                            query.getInt("colorBlueInt"));

               Event event = new Event(query.getString("eventTitle"),
                       query.getString("eventDescription"),
                       null,
                       eventColor,
                       query.getInt("day"),
                       query.getInt("month"),
                       query.getInt("year"),
                       startTime,
                       endTime,
                       query.getString("eventLocation"));
               events.add(event);
           }
       } catch (SQLException | ParseException e) {
           System.out.println("Problem with formatEventQuery");
           e.printStackTrace();
       }
   }

   public void setSelectedMonthsEvents() {
       this.selectedMonthsEvents = getSelectedEvents();
   }

   public ArrayList<Event> getDaysEvents() {
       ResultSet eventsQuery = db.getDaysEvents(currentYear, currentMonth, currentDay);
       ArrayList<Event> events = new ArrayList<Event>();
       formatEventQuery(eventsQuery, events);
       try {
           eventsQuery.close();
       } catch (SQLException e) {
           e.printStackTrace();
       }
       db.closeConnection();
       return events;
   }

   public ArrayList<Event> getSelectedEvents() {
       ResultSet eventsQuery = db.getSelectedEvents(selectedYear, selectedMonth);
       ArrayList<Event> events = new ArrayList<Event>();
       formatEventQuery(eventsQuery, events);
       try {
           eventsQuery.close();
       } catch (SQLException e) {
           e.printStackTrace();
       }
       db.closeConnection();
       return events;
   }

   public ArrayList<Event> getMonthsEvents() {
       ResultSet eventsQuery = db.getMonthsEvents(currentMonth, currentYear);
       ArrayList<Event> events = new ArrayList<Event>();
       formatEventQuery(eventsQuery, events);
       try {
           eventsQuery.close();
       } catch (SQLException e) {
           e.printStackTrace();
       }
       db.closeConnection();
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


   public void insertEvent(Event userInput) {
       db.insertEvent(1, userInput.getStart().toString(), userInput.getEnd().toString(), userInput.getDay(),
              userInput.getMonth(), userInput.getYear(), userInput.getColor().getRed(), userInput.getColor().getGreen(),
               userInput.getColor().getBlue(), userInput.getTitle(),userInput.getDescription(),
               userInput.getLocation());
       currentDayEvents.add(userInput);
       db.closeConnection();
       System.out.println(getCurrentDayEvents());
       notifySubscribers();
   }

   public void addSubscriber (PlannerListener aSub) {
       subscribers.add(aSub);
   }

   private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
   }

}
