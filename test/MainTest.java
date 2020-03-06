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
    public void setup() throws ClassNotFoundException, SQLException {
        test.startUp();
    }

    @Test
    public void exampleTest(){
        assertEquals(4, 2 + 2);
    }



}
