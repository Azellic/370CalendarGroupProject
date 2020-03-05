package Model;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//test
public class Event extends CalendarItem {
    protected Time start;
    protected Time end;
    protected int day;
    protected int month;
    protected int year;
    protected String location;

    public Event(String title, String description, Course course, Color color,
                 int day, int month, int year, Time start, Time end, String location) {
        super(title, description, course, color);
        this.start = start;
        this.end = end;
        this.day = day;
        this.month = month;
        this.year = year;
        this.location = location;
    }
    public Time getStart() {
        return start;
    }
    public Time getEnd() {
        return end;
    }
    public int getDay() {
        return day;

    }
    public int getMonth() {
        return month;
    }
    public int getYear() {
        return year;
    }
    public String getLocation(){
        return location;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public void setEnd(Time end) {
        this.end = end;
    }
    @Override
    public String toString() {
        return "Event{" +
                "start=" + start +
                ", end=" + end +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", location='" + location + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    private static void formatQuery(ResultSet query, ArrayList<Event> events) throws SQLException, ParseException {
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


    public static ArrayList<Event> getMonthsEvents(int month) throws SQLException,
            ClassNotFoundException, ParseException {
        DataBase db = new DataBase();
        db.startUp();
        ResultSet eventsQuery = db.getMonthsEvents(month);
        ArrayList<Event> events = new ArrayList<Event>();
        formatQuery(eventsQuery, events);
        return events;
    }
    public static ArrayList<Event> getDaysEvents(int year, int month, int day) throws SQLException,
            ClassNotFoundException, ParseException {
        DataBase db = new DataBase();
        db.startUp();
        ResultSet eventsQuery = db.getDaysEvents(year, month, day);
        ArrayList<Event> events = new ArrayList<>();
        formatQuery(eventsQuery, events);
        return events;
    }

    public static ArrayList<Event> getEvents() throws SQLException, ClassNotFoundException, ParseException {
        DataBase db = new DataBase();
        db.startUp();

        ResultSet eventsQuery = db.displayEvents();
        ArrayList<Event> events = new ArrayList<>();
        formatQuery(eventsQuery, events);
        return events;
    }

    public static void createEvent(String startTime, String endTime, int day, int month,
    int year, String eventTitle, String eventDescription, String eventLocation) throws SQLException, ClassNotFoundException {
        DataBase db = new DataBase();
        db.startUp();

        ResultSet eventResult = db.displayEvents();
        db.insertEvent(1, startTime,
                endTime,
                day,
                month,
                year,
                eventTitle,
                eventDescription,
                eventLocation);

    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {
        DataBase db = new DataBase();
        db.startUp();

        ResultSet eventResult = db.displayEvents();
        if (!eventResult.next()) {
            db.insertEvent(1,
                    "9:30",
                    "10:30",
                    1,
                    3,
                    2020,
                    "CMPT370 Project",
                    "Write code for the project",
                    "STM College");
            db.insertEvent(1,
                    "9:30",
                    "10:30",
                    1,
                    4,
                    2020,
                    "CMPT370 Project",
                    "Write code for the project",
                    "STM College");
            db.insertEvent(1,
                    "9:30",
                    "10:30",
                    5,
                    3,
                    2020,
                    "CMPT370 Project",
                    "Write code for the project",
                    "STM College");
            db.insertEvent(1,
                    "9:30",
                    "10:30",
                    5,
                    3,
                    2020,
                    "CMPT370 Project",
                    "Write code for the project",
                    "STM College");
        }
        System.out.println(getEvents());
        System.out.println(getMonthsEvents(4));
        System.out.println(getDaysEvents(2020, 4, 2));
    }
}
