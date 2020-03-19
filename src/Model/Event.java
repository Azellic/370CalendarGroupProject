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
    public static void createEvent(String startTime, String endTime, int day, int month,
    int year, String eventTitle, String eventDescription, String eventLocation, Color color) throws SQLException, ClassNotFoundException {
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
                eventLocation,
                color.toString());

    }
}
