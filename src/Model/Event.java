package Model;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//test
public class Event extends CalendarItem {
    protected Date start;
    protected Date end;
    protected int day;
    protected int month;
    protected int year;
    protected String location;

    public Event(String title, String description, Course course, Color color,
                 int day, int month, int year, Date start, Date end, String location) {
        super(title, description, course, color);
        this.start = start;
        this.end = end;
        this.day = day;
        this.month = month;
        this.year = year;
        this.location = location;
    }
    public int getDay() {
        return day;

    }
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public static ArrayList<Event> getEvents() throws SQLException, ClassNotFoundException {
        DataBase db = new DataBase();
        db.startUp();

        ResultSet eventsQuery = db.displayEvents();
        ArrayList<Event> events = new ArrayList<Event>();
        while(eventsQuery.next()){
            Event event = new Event(eventsQuery.getString("eventTitle"), null,
                    null, null, eventsQuery.getInt("day"),
                    eventsQuery.getInt("month"), eventsQuery.getInt("year"), null,
                    null, null);
            events.add(event);
        }
        return events;
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Event eventObj = new Event(null, null, null, null, 1, 1,
                1, null, null, null);
        System.out.println(getEvents().get(1));

    }
}
