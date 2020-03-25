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

    public void setSelectedCourse(String selectedCourse) {
        this.selectedCourse = selectedCourse;
        System.out.println(selectedCourse);
        ArrayList<Assessment> assessments = getSpecificCourseAssessmentList(selectedCourse);
        setAssessmentsList(assessments);
        System.out.println(getAssessmentList());
        setMinimumGrade(assessments);
        setAverageGrade(assessments);
        System.out.println("averageGrade = " + getAverageGrade());
        System.out.println("minimumGrade = " + getMinimumGrade());
    }

    public void setMinimumGrade(ArrayList<Assessment> assessments) {
        float weightLeft;
        int nonWeightedMark = 0;
        int countOfNoWeight = 0;
        double weightedGrade = 0;
        for (Assessment assessment : assessments) {
            if (assessment.getWeight() == 0) {
                countOfNoWeight += 1;
                nonWeightedMark += assessment.getMark();
            }
            weightedGrade = weightedGrade + (assessment.getMark() * (assessment.getWeight()/100));
        }
        this.minimumGrade = weightedGrade;
    }

    public double getMinimumGrade() {
        return this.minimumGrade;
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
        return this.averageGrade;
    }

    public ArrayList<Assessment> getAssessmentList() {
        return assessments;
    }

    private ArrayList<Assessment> formatAssessmentQuery(ResultSet query, ArrayList<Assessment> assessments){
        try {
            while(query.next()) {
                Assessment assessment = new Assessment(query.getString("assessmentTitle"),
                        null, query.getInt("grade"),
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
        return formatAssessmentQuery(assessmentsQuery, assessments);
    }

    public ArrayList<Assessment> getAssessmentsFromDB() {
        ResultSet assessmentsQuery = db.getAllAssessments();
        ArrayList<Assessment> assessments = new ArrayList<>();
        return formatAssessmentQuery(assessmentsQuery, assessments);
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
        db.insertAssessment(userInput.getCourseTitle(), userInput.getWeight(), userInput.getMark(),
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
