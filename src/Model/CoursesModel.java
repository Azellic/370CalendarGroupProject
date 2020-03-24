package Model;

import View.PlannerListener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CoursesModel {
    ArrayList<Course> courses;
    ArrayList<Assessment> assessments;
    ArrayList<PlannerListener> subscribers;
    DataBase db;

    public CoursesModel() {
        subscribers = new ArrayList<>();
        db = new DataBase();
        courses = getCoursesFromDB();
        assessments = getAssessmentsFromDB();
    }

    public ArrayList<Course> getCourseList() {
        return courses;
    }

    public ArrayList<Assessment> getAssessmentList() {
        return assessments;
    }

    public ArrayList<Assessment> getAssessmentsFromDB() {
        ResultSet assessmentsQuery = db.getAllAssessments();
        ArrayList<Assessment> assessments = new ArrayList<>();
        try {
            while(assessmentsQuery.next()) {
                Assessment assessment = new Assessment(assessmentsQuery.getString("assessmentTitle"),
                        null, assessmentsQuery.getInt("grade"),
                        assessmentsQuery.getInt("day"), assessmentsQuery.getInt("month"),
                        assessmentsQuery.getInt("year"),
                        assessmentsQuery.getString("description"),
                        assessmentsQuery.getFloat("weight"));
                assessments.add(assessment);
            }
        } catch (SQLException e) {
            System.out.println("Problem adding Assessments to assessmentList");
            e.printStackTrace();
        }
        db.closeConnection();
        return assessments;
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

    public void insertAssessment(Assessment userInput) {

        db.insertAssessment(0, userInput.getWeight(), userInput.getMark(),
                userInput.getTitle(), userInput.getDescription(), userInput.getDay(), userInput.getMonth(),
                userInput.getYear());
        getAssessmentList().add(userInput);
        db.closeConnection();
        System.out.println(getAssessmentList());
        notifySubscribers();
    }

    public void addSubscriber (PlannerListener aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }
}
