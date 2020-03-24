package Model;

import java.awt.*;

public class CalendarItem {

    protected String title;
    protected String description;
    private Color color;
    private String courseName;
    //private Course course;


    public CalendarItem(String title, String description, String courseName, Color color){
        this.title = title;
        this.description = description;
        this.courseName = courseName;
        //this.course = course;
        this.color = color;
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

    public void setDescription(String description){
        this.description = description;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    //    public Course getCourse(){
//        return this.course;
//    }

//    public void setCourse(Course course){
//        this.course = course;
//    }

//    public void setCourse(String value) {
//        //TODO: find course by name and assign
//    }


    public Color getColor(){
        return this.color;
    }

    public void setColor(Color color){
        this.color = color;
    }

}
