package Model;
import java.awt.*;
import java.util.Date;

public class Event extends CalendarItem {
    protected Date start;
    protected Date end;
    protected String location;
    protected int type;

    public Event(String title, String description, String tags, Color color, int type) {
        super(title, description, tags, color);
        this.type = type;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }
}
