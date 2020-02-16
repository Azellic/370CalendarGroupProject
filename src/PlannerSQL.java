import java.sql.*;

public class PlannerSQL {
    private static Connection con;

    /**
     * Sets the connection to the database and then calls initialise method
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:plannerDB.db");
        initialize();
    }
    /**
     * Checks if the tables are initialized
     * @throws SQLException
     */
    private void initialize() throws SQLException {
        Statement state = con.createStatement();
        // Check if all the tables exist

        ResultSet tagTable = state.executeQuery("SELECT name FROM sqlite_master WHERE " +
                "type='table' AND name='tag';");
        if (!tagTable.next()){
            System.out.println("Building the tag table and putting some data in it");
            state.execute("CREATE TABLE tag" +
                    "(tagID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "tagName VARCHAR);");
        }
        // SQLite does not store dates, they are manipulated when you call for the data
        // they are saved as integers now but can be changed later
        ResultSet eventTable = state.executeQuery("SELECT name FROM sqlite_master WHERE " +
                "type='table' AND name='event';");
        if (!eventTable.next()){
            System.out.println("Building the event table and putting some data in it");
            state.execute("CREATE TABLE event" +
                    "(eventID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "tagID INTEGER," +
                    "startTime INTEGER," +
                    "endTime INTEGER," +
                    "date INTEGER," +
                    "eventTitle VARCHAR," +
                    "FOREIGN KEY(tagID) REFERENCES tag(tagID)" +
                    "ON DELETE CASCADE);");
        }

        ResultSet assessmentTable = state.executeQuery("SELECT name FROM sqlite_master WHERE " +
                "type='table' AND name='assessment';");
        if(!assessmentTable.next()) {
            System.out.println("Building the assessment table and putting data in it");
            state.execute("CREATE TABLE assessment" +
                    "(assessmentID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "tagID INTEGER," +
                    "weight INTEGER," +
                    "grade REAL," +
                    "assessmentTitle VARCHAR," +
                    "FOREIGN KEY(tagID) REFERENCES tag(tagID)" +
                    "ON DELETE CASCADE);");
        }
        // SQLite does not have booleans so completed will be 1 or 0
        ResultSet taskTable = state.executeQuery("SELECT name FROM sqlite_master WHERE " +
                "type='table' AND name='task';");
        if(!taskTable.next()) {
            System.out.println("Building the task table and putting data in it");
            state.execute("CREATE TABLE task" +
                    "(taskID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "tagID INTEGER," +
                    "DueDate VARCHAR," +
                    "taskTitle VARCHAR," +
                    "completed VARCHAR," +
                    "FOREIGN KEY(tagID) REFERENCES tag(tagID)" +
                    "ON DELETE CASCADE);");
        }
    }
    public void startUp() throws SQLException, ClassNotFoundException {
        getConnection();
    }
}
