import java.sql.*;

public class PlannerSQL {
    private static Connection con;

    /**
     * Starts the database
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void startUp() throws SQLException, ClassNotFoundException {
        getConnection();
    }

    /**
     * Inserts values into the tag table
     * @param tagName
     * @throws SQLException
     */
    public void insertTag(String tagName) throws SQLException {
        PreparedStatement prep = con.prepareStatement("INSERT INTO tag(tagName) VALUES(?);");
        prep.setString(1,tagName);
        prep.executeUpdate();
    }

    /**
     * Inserts values into the event table
     * @param tagID
     * @param startTime
     * @param endTime
     * @param day
     * @param month
     * @param year
     * @param eventTitle
     * @throws SQLException
     */
    public void insertEvent(int tagID, String startTime, String endTime,
            int day, int month, int year, String eventTitle) throws SQLException {
        PreparedStatement prep = con.prepareStatement("INSERT INTO event(tagID, startTime, endTime, day" +
                ", month, year, eventTitle) VALUES (?,?,?,?,?,?,?);");
        prep.setInt(1,tagID);
        prep.setString(2,startTime);
        prep.setString(3,endTime);
        prep.setInt(4, day);
        prep.setInt(5, month);
        prep.setInt(6,year);
        prep.setString(7,eventTitle);
        prep.executeUpdate();

    }

    /**
     * Inserts values into the task table
     * @param tagID
     * @param dueDate
     * @param taskTitle
     * @throws SQLException
     */
    public void insertTask(int tagID, String dueDate, String taskTitle) throws SQLException {
        PreparedStatement prep = con.prepareStatement("INSERT INTO task(tagID, DueDate, taskTitle,completed)" +
                "VALUES(?,?,?,?);");
        prep.setInt(1,tagID);
        prep.setString(2,dueDate);
        prep.setString(3,taskTitle);
        prep.setInt(4,0);
        prep.executeUpdate();
    }

    /**
     * Inserts values into the assessment table
     * @param tagID
     * @param weight
     * @param grade
     * @param assessmentTitle
     * @throws SQLException
     */
    public void insertAssessment(int tagID, int weight, float grade, String assessmentTitle) throws SQLException {
        PreparedStatement prep = con.prepareStatement("INSERT INTO assessment(TAGID, WEIGHT, " +
                "GRADE, ASSESSMENTTITLE) VALUES(?,?,?,?);");
        prep.setInt(1, tagID);
        prep.setInt(2,weight);
        prep.setFloat(3,grade);
        prep.setString(4, assessmentTitle);
    }

    /**
     * Returns all values in the event table
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ResultSet displayEvents() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        Statement state = con.createStatement();
        return state.executeQuery("SELECT * FROM event;");
    }

    /**
     * Returns all values in the tag table
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ResultSet displayTags() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        Statement state = con.createStatement();
        return state.executeQuery("SELECT * FROM tag");
    }

    /**
     * Returns all values in the assessment table
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ResultSet displayAssessments() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        Statement state = con.createStatement();
        return state.executeQuery("SELECT * FROM assessment");
    }

    /**
     * Returns all values in the tasks table
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ResultSet displayTasks() throws SQLException, ClassNotFoundException {
        if (con == null){
            getConnection();
        }
        Statement state = con.createStatement();
        return state.executeQuery("SELECT * FROM task");
    }

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
                    "startTime VARCHAR," +
                    "endTime VARCHAR," +
                    "day INTEGER," +
                    "month INTEGER," +
                    "year INTEGER," +
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
                    "dueDate VARCHAR," +
                    "taskTitle VARCHAR," +
                    "completed VARCHAR," +
                    "FOREIGN KEY(tagID) REFERENCES tag(tagID)" +
                    "ON DELETE CASCADE);");
        }
    }

}
