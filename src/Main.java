import Controller.*;
import Model.Calendar;
//import Model.CalendarItem;
import Model.CoursesModel;
import Model.TaskBoardModel;
import View.*;

import java.sql.ResultSet;
import java.sql.SQLException;
// Main execution for the app
// test
public class Main {//extends Application{
    //Models
    Calendar calendarModel;
    CoursesModel coursesModel;
    TaskBoardModel taskModel;
    //Controllers
    CalendarController calController;
    DashboardController dashController;
    DayTabController daytabController;
    GradeTabController gradeController;
    TaskTabController taskController;
    //Views
    CalendarView calendarView;
    Dashboard dashboard;
    DaySidebar dayView;
    GradeSidebar gradeView;
    TaskSidebar taskView;

    /*
    @Override
    public void start(Stage primaryStage) {

    }*/
    // adding same comment for testing
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DataBase test = new DataBase();
        test.startUp();

        //launch(args);

        // Insert values careful not to add repeats on multiple runs
        ResultSet courseResult = test.displayCourses();
        if (!courseResult.next()){
            test.insertCourse("CMPT370");
            test.insertCourse("CMPT340");
            test.insertCourse("MATH110");
            test.insertCourse("WORK");
        } else {
            System.out.println("\nTa Table Testing");
            System.out.println(courseResult.getInt("courseID")+ " "+ courseResult.getString("courseName"));
            while(courseResult.next()){
                System.out.println(courseResult.getInt("courseID")+ " "+ courseResult.getString("courseName"));
            }
        }

        ResultSet eventResult = test.displayEvents();
        if (!eventResult.next()){
            test.insertEvent(1, "10:30", "11:30",
                    3, 3, 2020, "Midterm");
            test.insertEvent(2, "11:30", "12:30",
                    27, 3, 2020, "Midterm");
            test.insertEvent(3, "7:30", "11:30",
                    7, 3, 2020, "Midterm");
            test.insertEvent(4, "10:30", "11:30",
                    3, 2, 2020, "Work Shift");
            test.insertEvent(1, "6:00", "11:30",
                    2, 6, 2020, "Class");
            test.insertEvent(2, "12:30", "2:45",
                    2, 5, 2020, "Class");
            test.insertEvent(3, "8:30", "11:30",
                    7, 8, 2020, "Class");
            test.insertEvent(4, "7:00", "10:00",
                    10, 3, 2020, "Work Shift");
        } else {
            System.out.println("\nEvent Table Tests");
            System.out.println(eventResult.getInt("eventID") +" "+ eventResult.getInt("courseID")+" "+
                    eventResult.getString("startTime")+" "+eventResult.getString("endTime")+ " "+
                    eventResult.getInt("day")+ " " + eventResult.getInt("month")+ " "+
                    eventResult.getInt("year")+ " " + eventResult.getString("eventTitle"));
            while(eventResult.next()){
                System.out.println(eventResult.getInt("eventID") +" "+ eventResult.getInt("courseID")+" "+
                        eventResult.getString("startTime")+" "+eventResult.getString("endTime")+ " "+
                        eventResult.getInt("day")+ " " + eventResult.getInt("month")+ " "+
                        eventResult.getInt("year")+ " " + eventResult.getString("eventTitle"));
            }
        }
        ResultSet assessmentResult = test.displayAssessments();
        if (!assessmentResult.next()){
            test.insertAssessment(1, 25, 100, "Midterm");
            test.insertAssessment(2, 50, 25, "Final");
            test.insertAssessment(3, 35, 37.5, "Final Project");
            test.insertAssessment(1, 5, 55.5, "assignment 1");
            test.insertAssessment(2, 10, 65, "Assignment 3");
        } else {
            System.out.println("\nAssessment Table Tests");
            System.out.println(assessmentResult.getInt("assessmentId")+" "+assessmentResult.getInt("courseID")+" "+
                    assessmentResult.getInt("grade")+" "+assessmentResult.getString("assessmentTitle"));
            while(assessmentResult.next()){
                System.out.println(assessmentResult.getInt("assessmentId")+" "+assessmentResult.getInt("courseID")+" "+
                        assessmentResult.getInt("grade")+" "+assessmentResult.getString("assessmentTitle"));
            }
        }
        ResultSet tasksResult = test.displayTasks();
        if (!tasksResult.next()) {
            test.insertTask(1, 6, 4, 2020, "Assignment");
            test.insertTask(1, 5, 8, 2020, "Assignment");
            test.insertTask(2, 3, 4, 2020, "clean room");
            test.insertTask(3, 5, 6, 2020, "Do Dishes");
        } else {
            System.out.println("\nTasks Table Tests");
            System.out.println(tasksResult.getInt("taskID")+" "+tasksResult.getInt("courseID")+" "+
                    tasksResult.getInt("dueDay")+" "+tasksResult.getInt("dueMonth")+" "+
                    tasksResult.getInt("dueYear")+" "+tasksResult.getString("taskTitle"));
            while(tasksResult.next()) {
                System.out.println(tasksResult.getInt("taskID")+" "+tasksResult.getInt("courseID")+" "+
                        tasksResult.getInt("dueDay")+" "+tasksResult.getInt("dueMonth")+" "+
                        tasksResult.getInt("dueYear")+" "+tasksResult.getString("taskTitle"));
            }
        }

    }
}
