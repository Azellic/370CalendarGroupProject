package Model;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.Time;

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
    public String getStart() {
        return start.toString();
    }
    public String getEnd() {
        return end.toString();
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
    public static void createEvent(String startTime, String endTime, int day, int month,
    int year, String eventTitle, String eventDescription, String eventLocation) {
        DataBase db = new DataBase();
        db.startUp();

        ResultSet eventResult =  db.getAllEvents();
        db.insertEvent(1, startTime,
                endTime,
                day,
                month,
                year,
                eventTitle,
                eventDescription,
                eventLocation);

    }
}
