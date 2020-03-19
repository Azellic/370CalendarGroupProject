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
    public void startUp() throws SQLException, ClassNotFoundException {
        getConnection();
    }
    /**
     * Sets the connection to the database and then calls initialise method
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:plannerDB.db");
        initialize();
    }

    private void initialize() throws SQLException {
        Statement state = con.createStatement();
        try (ResultSet courseTable = state.executeQuery("SELECT name FROM sqlite_master " +
                "WHERE type='table' AND name='course';")) {

            if (!courseTable.next()) {
                state.execute("CREATE TABLE course" +
                        "(courseID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "courseName VARCHAR);");
            }
        }

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
                    "Colour VARCHAR," +
                    "FOREIGN KEY(courseID) REFERENCES course(courseID)" +
                    "ON DELETE CASCADE);");
        }

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
    }

    public void insertCourse(String courseName) throws SQLException {
        PreparedStatement prep = con.prepareStatement("INSERT INTO course(courseName) VALUES(?);");
        prep.setString(1,courseName);
        prep.executeUpdate();
    }

    public void insertEvent(int courseID, String startTime, String endTime,
                            int day, int month, int year, String eventTitle, String eventDescription ,
                            String eventLocation, String color) throws SQLException {
        PreparedStatement prep = con.prepareStatement("INSERT INTO event(courseID, startTime, endTime, day," +
                " month, year, eventTitle, eventDescription, eventLocation, Colour) VALUES (?,?,?,?,?,?,?,?,?,?);");
        prep.setInt(1,courseID);
        prep.setString(2,startTime);
        prep.setString(3,endTime);
        prep.setInt(4, day);
        prep.setInt(5, month);
        prep.setInt(6,year);
        prep.setString(7,eventTitle);
        prep.setString(8,eventDescription);
        prep.setString(9,eventLocation);
        prep.setString(10,color);
        prep.executeUpdate();
    }

    public void insertTask(int courseID, int dueDay, int dueMonth, int dueYear, String taskTitle) throws SQLException {
        PreparedStatement prep = con.prepareStatement("INSERT INTO task(courseID, dueDay, dueMonth, dueYear," +
                " taskTitle,completed) VALUES(?,?,?,?,?,?);");
        prep.setInt(1,courseID);
        prep.setInt(2,dueDay);
        prep.setInt(3,dueMonth);
        prep.setInt(4,dueYear);
        prep.setString(5,taskTitle);
        prep.setInt(6,0);
        prep.executeUpdate();
    }

    public void insertAssessment(int courseID, int weight, double grade, String assessmentTitle) throws SQLException {
        PreparedStatement prep = con.prepareStatement("INSERT INTO assessment(courseID, WEIGHT, " +
                "GRADE, ASSESSMENTTITLE) VALUES(?,?,?,?);");
        prep.setInt(1, courseID);
        prep.setInt(2, weight);
        prep.setDouble(3, grade);
        prep.setString(4, assessmentTitle);
        prep.executeUpdate();
    }

    public ResultSet displayEvents() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        Statement state = con.createStatement();
        return state.executeQuery("SELECT * FROM event;");
    }
    public ResultSet getMonthsEvents(int month) throws SQLException, ClassNotFoundException {
        if (con == null){
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement("SELECT * FROM event WHERE month = ?; ");
        prep.setInt(1, month);
        return prep.executeQuery();
    }
    public ResultSet getDaysEvents(int year, int month, int day) throws SQLException, ClassNotFoundException {
        if (con == null){
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement(
                "SELECT * FROM event WHERE year = ? AND month = ? AND day = ?;");
        prep.setInt(1, year);
        prep.setInt(2, month);
        prep.setInt(3, day);
        return prep.executeQuery();
    }

    public ResultSet getSelectedEvents(int year, int month) throws SQLException, ClassNotFoundException {
        if (con == null){
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement(
                "SELECT * FROM event WHERE year = ? AND month = ?;"
        );
        prep.setInt(1, year);
        prep.setInt(2, month);
        return prep.executeQuery();
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
        return state.executeQuery("SELECT * FROM task");
    }

}
