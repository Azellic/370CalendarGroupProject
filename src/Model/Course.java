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
    private ArrayList<Course> courseList;
    private DataBase db;

    public Course(String title, String description, String instructor){
        this.title = title;
        this.description = description;
        this.instructor = instructor;
        this.courseList = getCoursesFromDB();
        db = new DataBase();
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

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    public ArrayList<Course> getCoursesFromDB() {
        ResultSet coursesQuery = db.getAllCourses();
        ArrayList<Course> courses = new ArrayList<>();
        try {
            while(coursesQuery.next()){
                Course course = new Course(coursesQuery.getString("courseName"),
                                           coursesQuery.getString("courseInstructor"),
                                           coursesQuery.getString("courseDescription"));
                courses.add(course);
            }
        }catch (SQLException e){
            System.out.println("Problem added courses to the courseList");
            e.printStackTrace();
        }
        db.closeConnection();
        return courses;
    }

    public void insertCourse(Course userInput) {
        db.insertCourse(userInput.getTitle(), userInput.getInstructor(), userInput.getDescription());
        getCourseList().add(userInput);
        db.closeConnection();
        System.out.println(getCourseList());
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
