package Model;
import java.awt.*;
import java.util.Date;

//test
public class Event extends CalendarItem {
    protected Date start;
    protected Date end;
    protected String location;
    protected int type;

    public Event(String title, String description, Course course) {
        super(title, description, course);
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }
}
