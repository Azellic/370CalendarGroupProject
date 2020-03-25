import Model.Course;
import Model.CoursesModel;
import Model.DataBase;
import org.junit.*;


import java.io.File;
import java.nio.file.Files;
import java.sql.*;
import java.util.ArrayList;


import static org.junit.Assert.*;

public class MainTest {
    private static DataBase db;
    private static CoursesModel courseModel;
    @BeforeClass
    public static void setup() {
        // Make sure to start with a new database
        db = new DataBase();
        db.startUp();
        courseModel = new CoursesModel();
        db.insertAssessment("CMPT370", 50.0, 34, "Assignment1",
                "first Assignment", 1,1,1);
    }

    @Test
    public void exampleTest(){
        assertEquals(4, 2 + 2);
    }

    @Test
    public void insertCoursesTest(){
        Course course1 = new Course("CMPT370", "Software Engineering", "Kevin");
        Course course2 = new Course("CMPT360", "Mondal", "Algorithms");
        Course course3 = new Course("CMPT340", "Nadeem", "Programming Paradigms");
        ArrayList<Course> coursesDB;
        courseModel.insertCourse(course1);
        courseModel.insertCourse(course2);
        courseModel.insertCourse(course3);
        coursesDB = courseModel.getCoursesFromDB();
        assertTrue(course1.equalsByField(coursesDB.get(0)));
        assertTrue(course2.equalsByField(coursesDB.get(1)));
        assertTrue(course3.equalsByField(coursesDB.get(2)));



    }



}
