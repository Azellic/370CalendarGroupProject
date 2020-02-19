import java.sql.ResultSet;
import java.sql.SQLException;

public class main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        PlannerSQL test = new PlannerSQL();
        test.startUp();

        // Insert values careful not to add repeats on multiple runs
        ResultSet tagResult = test.displayTags();
        if (!tagResult.next()){
            test.insertTag("CMPT370");
            test.insertTag("CMPT340");
            test.insertTag("MATH110");
            test.insertTag("WORK");
        } else {
            System.out.println(tagResult.getInt("tagID")+ " "+ tagResult.getString("tagName"));
            while(tagResult.next()){
                System.out.println(tagResult.getInt("tagID")+ " "+ tagResult.getString("tagName"));
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
            System.out.println(eventResult.getInt("eventID") +" "+ eventResult.getInt("tagID")+" "+
                    eventResult.getString("startTime")+" "+eventResult.getString("endTime")+ " "+
                    eventResult.getInt("day")+ " " + eventResult.getInt("month")+ " "+
                    eventResult.getInt("year")+ " " + eventResult.getString("eventTitle"));
            while(eventResult.next()){
                System.out.println(eventResult.getInt("eventID") +" "+ eventResult.getInt("tagID")+" "+
                        eventResult.getString("startTime")+" "+eventResult.getString("endTime")+ " "+
                        eventResult.getInt("day")+ " " + eventResult.getInt("month")+ " "+
                        eventResult.getInt("year")+ " " + eventResult.getString("eventTitle"));
            }
        }
        ResultSet assessmentResult = test.displayAssessments();
        ResultSet tasksResult = test.displayTasks();

    }
}
