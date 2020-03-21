package Model;

import View.PlannerListener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CoursesModel {
    ArrayList<Course> courses;
    ArrayList<PlannerListener> subscribers;
    DataBase db;

    public CoursesModel() {
        subscribers = new ArrayList<>();
        db = new DataBase();
        courses = getCoursesFromDB();
    }

    public ArrayList<Course> getCourseList() {
        return courses;
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
        notifySubscribers();
    }
    public void addSubscriber (PlannerListener aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }
}
