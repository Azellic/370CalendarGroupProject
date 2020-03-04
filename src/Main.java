import Controller.*;
import Model.*;
import View.*;

import static javafx.application.Application.launch;

// Main execution for the app
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

    public static void main(String[] args) {
        launch(args);
    }
}
