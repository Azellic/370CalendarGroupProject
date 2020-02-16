import java.sql.ResultSet;
import java.sql.SQLException;

public class main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        PlannerSQL test = new PlannerSQL();
        ResultSet result;
        //test.startUp();
        //test.insertEvent(1,10,11,"January 6","Doctor");
        result = test.displayEvents();
        while(result.next()){
            System.out.println(result.getInt("eventID") +" "+ result.getInt("tagID")+" "+
                    result.getInt("startTime")+" "+result.getString("date")+" "+
                    result.getString("eventTitle"));
        }
    }
}
