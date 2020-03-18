package Model;

import java.sql.*;

public class DataBase {
    private static Connection con;

    /**
     * Starts the database
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
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

    /**
     * Sets the connection to the database and then calls initialise method
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void getConnection(){
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
                        "courseName VARCHAR);");
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
                        "courseID INTEGER," +
                        "dueDay INTEGER," +
                        "dueMonth INTEGER," +
                        "dueYear INTEGER," +
                        "taskTitle VARCHAR," +
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

    public void insertCourse(String courseName){
        try {
            PreparedStatement prep = con.prepareStatement("INSERT INTO course(courseName) VALUES(?);");
            prep.setString(1, courseName);
            prep.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem inserting course");
            e.printStackTrace();
        }
    }

    public void insertEvent(int courseID, String startTime, String endTime,
                            int day, int month, int year, String eventTitle, String eventDescription ,
                            String eventLocation){
        try {
            if (con == null) {
                getConnection();
            }
            PreparedStatement prep = con.prepareStatement("INSERT INTO event(courseID, startTime, endTime, day," +
                    " month, year, eventTitle, eventDescription, eventLocation) VALUES (?,?,?,?,?,?,?,?,?);");
            prep.setInt(1, courseID);
            prep.setString(2, startTime);
            prep.setString(3, endTime);
            prep.setInt(4, day);
            prep.setInt(5, month);
            prep.setInt(6, year);
            prep.setString(7, eventTitle);
            prep.setString(8, eventDescription);
            prep.setString(9, eventLocation);
            prep.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Problem inserting Event");
            e.printStackTrace();
        }
    }

    public void insertTask(int courseID, int dueDay, int dueMonth, int dueYear, String taskTitle) {
        try {
            PreparedStatement prep = con.prepareStatement("INSERT INTO task(courseID, dueDay, dueMonth, dueYear," +
                    " taskTitle,completed) VALUES(?,?,?,?,?,?);");
            prep.setInt(1, courseID);
            prep.setInt(2, dueDay);
            prep.setInt(3, dueMonth);
            prep.setInt(4, dueYear);
            prep.setString(5, taskTitle);
            prep.setInt(6, 0);
            prep.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Problem inserting Task");
            e.printStackTrace();
        }
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

    public ResultSet displayEvents() {
        ResultSet resultQuery = null;
        try {
            setConnection();
            Statement state = con.createStatement();
            resultQuery = state.executeQuery("SELECT * FROM event;");
        } catch (SQLException e) {
            System.out.println("Problem in displayEvents");
            e.printStackTrace();
        }
//        finally {
//            try {
//                con.close();
//            } catch (SQLException e) {
//                System.out.println("Problem Closing after displayEvents called");
//            }
//        }
        return resultQuery;
    }
    public ResultSet getMonthsEvents(int month) {
        ResultSet resultQuery = null;
        try {
            //if (con == null) {
            //    getConnection();
            //}
            setConnection();
            PreparedStatement prep = con.prepareStatement("SELECT * FROM event WHERE month = ?; ");
            prep.setInt(1, month);
            resultQuery = prep.executeQuery();
        } catch (SQLException e) {
            System.out.println("Problem in getMonthsEvents");
            e.printStackTrace();
        }
//        finally {
//            try {
//                con.close();
//            } catch (SQLException e) {
//                System.out.println("Problem Closing after displayEvents called");
//            }
//        }
        return resultQuery;
    }
    public ResultSet getDaysEvents(int year, int month, int day) {
        ResultSet resultQuery = null;
        try {
            //if (con == null) {
            //    getConnection();
            //}
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
//        finally {
//            try {
//                con.close();
//            } catch (SQLException e) {
//                System.out.println("Problem Closing after displayEvents called");
//            }
//        }
        return resultQuery;
    }

    public ResultSet getSelectedEvents(int year, int month){
        ResultSet resultQuery = null;
        try {
            //if (con == null) {
            //    getConnection();
            //}
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
                System.out.println("Problem Closing after displayEvents called");
            }
        }
        return resultQuery;
    }

    public ResultSet displayCourses() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        Statement state = con.createStatement();
        return state.executeQuery("SELECT * FROM course");
    }
    public ResultSet displayAssessments() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        Statement state = con.createStatement();
        return state.executeQuery("SELECT * FROM assessment");
    }
    public ResultSet displayTasks() throws SQLException, ClassNotFoundException {
        if (con == null){
            getConnection();
        }
        Statement state = con.createStatement();
        ResultSet resultQuery = state.executeQuery("SELECT * FROM task");
        return resultQuery;

    }

}
