import Model.DataBase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class MainTest {
    DataBase test = new DataBase();
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException {
        DataBase test = new DataBase();
        test.startUp();
    }

    @Test
    public void exampleTest(){
        assertEquals(4, 2 + 2);
    }



}
