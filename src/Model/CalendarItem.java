package Model;

import java.awt.*;

public class CalendarItem {

    protected String title; // A title for the calendar Item

    protected String description; // A description of the item

    private int tags;

    private Color color;

    private course class;

    private assesment mark;

    public CalendarItem(String title, String description, String tags, Color color){
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.color = color;
        // call a function to add the new calendar item to the DB
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String title){
        this.title = description;
    }
    public String getColor(){
        return this.color;
    }

    public void setColor(String title){
        this.title = color;
    }



}
