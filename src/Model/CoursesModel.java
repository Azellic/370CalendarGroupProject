package Model;

import View.PlannerListener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CoursesModel {
    ArrayList<Course> courses;
    String selectedCourse;
    ArrayList<Assessment> assessments;
    ArrayList<PlannerListener> subscribers;
    DataBase db;
    double minimumGrade;
    double averageGrade;

    public CoursesModel() {
        subscribers = new ArrayList<>();
        db = new DataBase();
        courses = getCoursesFromDB();
    }

    public ArrayList<Course> getCourseList() {
        return courses;
    }

    public void setAssessmentsList(ArrayList<Assessment> assessments){
        this.assessments = assessments;
    }

    public String getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(String selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public void setMinimumGrade(ArrayList<Assessment> assessments) {
        double weightedGrade = 0;
        for (Assessment assessment : assessments) {
            weightedGrade = weightedGrade + (assessment.getMark() * (assessment.getWeight()/100));
        }
        this.minimumGrade = weightedGrade;
    }

    public double getMinimumGrade() {
        return minimumGrade;
    }

    public void setAverageGrade(ArrayList<Assessment> assessments) {
        double averageGrade = 0;
        double runningWeight = 0;
        for(Assessment assessment : assessments) {
            runningWeight += assessment.getWeight();
            averageGrade = averageGrade + (assessment.getMark() * (assessment.getWeight()/100));
        }
        this.averageGrade = (averageGrade / runningWeight) * 100;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public ArrayList<Assessment> getAssessmentList() {
        return assessments;
    }

    private ArrayList<Assessment> formatAssessmentQuery(ResultSet query, ArrayList<Assessment> assessments,
                                                        String courseName){
        try {
            while(query.next()) {
                Assessment assessment = new Assessment(query.getString("assessmentTitle"),
                        courseName, query.getInt("grade"),
                        query.getInt("day"), query.getInt("month"),
                        query.getInt("year"),
                        query.getString("description"),
                        query.getFloat("weight"));
                assessments.add(assessment);
            }
        } catch (SQLException e) {
            System.out.println("Problem adding Assessments to assessmentList");
            e.printStackTrace();
        } finally {
            try {
                query.close();
            } catch (SQLException e) {
                System.out.println("Problem closing formatAssessmentQuery");
                e.printStackTrace();
            }
        }
        db.closeConnection();
        return assessments;
    }

    public ArrayList<Assessment> getSpecificCourseAssessmentList(String courseName) {
        ResultSet assessmentsQuery = db.getSpecificCourseAssessments(courseName);
        ArrayList<Assessment> assessments = new ArrayList<>();
        return formatAssessmentQuery(assessmentsQuery, assessments, courseName);
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

    public void updateAssessmentList() {
        ArrayList<Assessment> assessments = getSpecificCourseAssessmentList(getSelectedCourse());
        setAssessmentsList(assessments);
        setMinimumGrade(assessments);
        setAverageGrade(assessments);
    }

    public void insertCourse(Course userInput) {
        db.insertCourse(userInput.getTitle(), userInput.getDescription(), userInput.getInstructor());
        getCourseList().add(userInput);
        db.closeConnection();
        System.out.println(getCourseList());
        notifySubscribers();
    }

    public void insertAssessment(Assessment userInput) {
        db.insertAssessment(userInput.getCourseTitle(), userInput.getWeight(), userInput.getMark(),
                userInput.getTitle(), userInput.getDescription(), userInput.getDay(), userInput.getMonth(),
                userInput.getYear());
        db.closeConnection();
        notifySubscribers();
    }

    public void addSubscriber (PlannerListener aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }
}
