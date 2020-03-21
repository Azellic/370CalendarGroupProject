package Model;

import View.PlannerListener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Course {
    private ArrayList<PlannerListener> subscribers;
    private String title;
    private String description;
    private String instructor;
    //private ArrayList<Course> courseList;
    //private DataBase db;

    public Course(String title, String description, String instructor){
        this.title = title;
        this.description = description;
        this.instructor = instructor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }


    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", instructor='" + instructor + '\'' +
                '}';
    }

    public void addSubscriber (PlannerListener aSub) {
        subscribers.add(aSub);
        notifySubscribers();
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }
}
