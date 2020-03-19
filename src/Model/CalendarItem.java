package Model;

import java.awt.*;

public class CalendarItem {

    protected String title; // A title for the calendar Item

    protected String description; // A description of the item

    private Color color;

    private Course course;

    private int ID;

    //private Assesment mark;

    public CalendarItem(String title, String description, Course course, Color color){
        this.title = title;
        this.description = description;
        this.course = course;
        this.color = color;
    }

    // *** the Getters and Setters ***

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Course getCourse(){
        return this.course;
    }

    public void setCourse(Course course){
        this.course = course;
    }

    public void setCourse(String value) {
        //TODO: find course by name and assign
    }

    /*
    public Assesment getMark() {
        return mark;
    }

    public void setMark(Assesment mark) {
        this.mark = mark;
    }
    */

    public Color getColor(){
        return this.color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
