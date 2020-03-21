package Model;

import java.sql.*;

public class DataBase {
    protected static Connection con;

    public void startUp() {
        getConnection();
    }

    public void setConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:plannerDB.db");
        } catch(ClassNotFoundException | SQLException e){
            System.out.println("Problem with setConnection");
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try{
            con.close();
        } catch (SQLException e){
            System.out.println("Problem closing connection");
            e.printStackTrace();
        }
    }

    protected void getConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:plannerDB.db");
            initialize();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Problem with getConnection");
            e.printStackTrace();
        }
    }

    private void initialize() {
        Statement state = null;
        try {
            state = con.createStatement();
            ResultSet courseTable = state.executeQuery("SELECT name FROM sqlite_master " +
                    "WHERE type='table' AND name='course';");

            if (!courseTable.next()) {
                state.execute("CREATE TABLE course" +
                        "(courseID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "courseName VARCHAR," +
                        "courseInstructor VARCHAR, " +
                        "courseDescription VARCHAR);");
            }
        } catch (SQLException e) {
            System.out.println("Problem creating the course table");
            e.printStackTrace();
        }
        try {
            ResultSet eventTable = state.executeQuery("SELECT name FROM sqlite_master WHERE " +
                    "type='table' AND name='event';");
            if (!eventTable.next()){
                state.execute("CREATE TABLE event" +
                        "(eventID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "courseID INTEGER," +
                        "startTime VARCHAR," +
                        "endTime VARCHAR," +
                        "day INTEGER," +
                        "month INTEGER," +
                        "year INTEGER," +
                        "colorRedInt INTEGER," +
                        "colorGreenInt INTEGER," +
                        "colorBlueInt INTEGER," +
                        "eventTitle VARCHAR," +
                        "eventDescription VARCHAR," +
                        "eventLocation VARCHAR," +
                        "FOREIGN KEY(courseID) REFERENCES course(courseID)" +
                        "ON DELETE CASCADE);");
            }
        } catch (SQLException e) {
            System.out.println("Problem creating the event table");
            e.printStackTrace();
        }
        try {
            ResultSet assessmentTable = state.executeQuery("SELECT name FROM sqlite_master WHERE " +
                    "type='table' AND name='assessment';");
            if(!assessmentTable.next()) {
                state.execute("CREATE TABLE assessment" +
                        "(assessmentID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "courseID INTEGER," +
                        "weight INTEGER," +
                        "grade REAL," +
                        "assessmentTitle VARCHAR," +
                        "FOREIGN KEY(courseID) REFERENCES course(courseID)" +
                        "ON DELETE CASCADE);");
            }
        } catch (SQLException e){
            System.out.println("Problem creating the assessment Table");
            e.printStackTrace();
        }
        try {
            ResultSet taskTable = state.executeQuery("SELECT name FROM sqlite_master WHERE " +
                    "type='table' AND name='task';");
            if(!taskTable.next()) {
                state.execute("CREATE TABLE task" +
                        "(taskID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "taskTitle VARCHAR," +
                        "taskDescription VARCHAR," +
                        "courseID INTEGER," +
                        "colorRedInt INTEGER," +
                        "colorGreenInt INTEGER," +
                        "colorBlueInt INTEGER," +
                        "dueDay INTEGER," +
                        "dueMonth INTEGER," +
                        "dueYear INTEGER," +
                        "dueTime VARCHAR," +
                        "completed VARCHAR," +
                        "FOREIGN KEY(courseID) REFERENCES course(courseID)" +
                        "ON DELETE CASCADE);");
            }
        } catch (SQLException e) {
            System.out.println("Problem creating task table");
            e.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("Problem closing after initializing");
            e.printStackTrace();
        }
    }

    public void insertCourse(String courseName, String courseInstructor, String courseDescription){
        PreparedStatement prep = null;
        try {
            setConnection();
            prep = con.prepareStatement("INSERT INTO course(courseName, " +
                    "courseInstructor, courseDescription) VALUES(?,?,?);");
            prep.setString(1, courseName);
            prep.setString(2, courseInstructor);
            prep.setString(3, courseDescription);
            prep.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem inserting course");
            e.printStackTrace();
        }
        finally {
            try {
                prep.close();
            } catch (SQLException e) {
                System.out.println("Problem closing insert course prepared statement");
                e.printStackTrace();
            }
        }
    }

    public ResultSet getAllCourses() {
        ResultSet resultQuery = null;
        try {
            setConnection();
            Statement state = con.createStatement();
            resultQuery = state.executeQuery("SELECT * FROM course");
        } catch (SQLException e) {
            System.out.println("Problem returning all courses");
            e.printStackTrace();
        }
        return resultQuery;
    }

    public void insertEvent(int courseID, String startTime, String endTime,
                            int day, int month, int year, int colorRedInt, int colorGreenInt, int colorBlueInt,
                            String eventTitle, String eventDescription ,
                            String eventLocation){
        PreparedStatement prep = null;
        try {
            setConnection();
            prep = con.prepareStatement("INSERT INTO event(courseID, startTime, endTime, day," +
                    " month, year, colorRedInt, colorGreenInt, colorBlueInt, eventTitle, " +
                    "eventDescription, eventLocation) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);");
            prep.setInt(1, courseID);
            prep.setString(2, startTime);
            prep.setString(3, endTime);
            prep.setInt(4, day);
            prep.setInt(5, month);
            prep.setInt(6, year);
            prep.setInt(7, colorRedInt);
            prep.setInt(8, colorGreenInt);
            prep.setInt(9, colorBlueInt);
            prep.setString(10, eventTitle);
            prep.setString(11, eventDescription);
            prep.setString(12, eventLocation);
            prep.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Problem inserting Event");
            e.printStackTrace();
        }
        finally {
            try {
                prep.close();
            } catch (SQLException e) {
                System.out.println("Problem closing prepared statement for insert");
                e.printStackTrace();
            }
        }
    }

    public ResultSet getAllEvents() {
        ResultSet resultQuery = null;
        try {
            setConnection();
            Statement state = con.createStatement();
            resultQuery = state.executeQuery("SELECT * FROM event;");
        } catch (SQLException e) {
            System.out.println("Problem in getting all Events");
            e.printStackTrace();
        }

        return resultQuery;
    }

    public ResultSet getMonthsEvents(int month, int year) {
        ResultSet resultQuery = null;
        try {
            setConnection();
            PreparedStatement prep = con.prepareStatement("SELECT * FROM event WHERE month = ? AND year = ?; ");
            prep.setInt(1, month);
            prep.setInt(2, year);
            resultQuery = prep.executeQuery();
        } catch (SQLException e) {
            System.out.println("Problem in getMonthsEvents");
            e.printStackTrace();
        }
        return resultQuery;
    }

    public ResultSet getDaysEvents(int year, int month, int day) {
        ResultSet resultQuery = null;
        try {
            setConnection();
            PreparedStatement prep = con.prepareStatement(
                    "SELECT * FROM event WHERE year = ? AND month = ? AND day = ?;");
            prep.setInt(1, year);
            prep.setInt(2, month);
            prep.setInt(3, day);
            resultQuery = prep.executeQuery();

        } catch(SQLException e){
            System.out.println("Problem with getDaysEvents");
            e.printStackTrace();
        }

        return resultQuery;
    }

    public ResultSet getSelectedEvents(int year, int month){
        ResultSet resultQuery = null;
        try {
            setConnection();
            PreparedStatement prep = con.prepareStatement(
                    "SELECT * FROM event WHERE year = ? AND month = ?;"
            );
            prep.setInt(1, year);
            prep.setInt(2, month);
            resultQuery = prep.executeQuery();
        } catch (SQLException e) {
            System.out.println("Problem with getSelectedEvents");
            e.printStackTrace();
        }
        finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Problem Closing after getSelectedEvents called");
            }
        }
        return resultQuery;
    }

    public void insertAssessment(int courseID, int weight, double grade, String assessmentTitle) {
        try {
            PreparedStatement prep = con.prepareStatement("INSERT INTO assessment(courseID, WEIGHT, " +
                    "GRADE, ASSESSMENTTITLE) VALUES(?,?,?,?);");
            prep.setInt(1, courseID);
            prep.setInt(2, weight);
            prep.setDouble(3, grade);
            prep.setString(4, assessmentTitle);
            prep.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Problem inserting assessment");
            e.printStackTrace();
        }
    }

    public ResultSet getAllAssessments() {
        ResultSet resultQuery = null;
        try {
            setConnection();
            Statement state = con.createStatement();
            resultQuery = state.executeQuery("SELECT * FROM assessment");
        } catch(SQLException e) {
            System.out.println("Problem getting all assessments");
            e.printStackTrace();
        }

        return resultQuery;
    }

    public void insertTask(String taskTitle, String taskDescription, int courseID,
                           int colorRedInt, int colorGreenInt, int colorBlueInt,
                           int dueDay, int dueMonth, int dueYear, String dueTime) {
        PreparedStatement prep = null;
        try {
            setConnection();
            prep = con.prepareStatement("INSERT INTO task(taskTitle, taskDescription, courseID, " +
                    "colorRedInt, colorGreenInt, colorBlueInt, dueDay, dueMonth, dueYear, dueTime) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?);");
            prep.setString(1, taskTitle);
            prep.setString(2, taskDescription);
            prep.setInt(3, courseID);
            prep.setInt(4, colorRedInt);
            prep.setInt(5, colorGreenInt);
            prep.setInt(6, colorBlueInt);
            prep.setInt(7, dueDay);
            prep.setInt(8, dueMonth);
            prep.setInt(9, dueYear);
            prep.setString(10, dueTime);
            prep.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Problem inserting Task");
            e.printStackTrace();
        } finally {
            try {
                prep.close();
            } catch (SQLException e) {
                System.out.println("Problem closing task statement ");
            }
        }
    }

    public ResultSet getAllTasks()  {
        ResultSet resultQuery = null;
        try {
            setConnection();
            Statement state = con.createStatement();
            resultQuery = state.executeQuery("SELECT * FROM task");
        } catch (SQLException e) {
            System.out.println("Problem getting all tasks");
            e.printStackTrace();
        }
        return resultQuery;

    }
}
