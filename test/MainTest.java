import Model.Assessment;
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
    public static Course course1;
    public static Course course2;
    public static Course course3;
    public static Assessment assessment1;
    public static Assessment assessment2;
    public static Assessment assessment3;
    public static Assessment assessment4;
    public static Assessment assessment5;
    public static Assessment assessment6;
    public static Assessment assessment7;
    public static Assessment assessment8;
    public static Assessment assessment9;
    public static Assessment assessment10;


    @BeforeClass
    public static void setup() {
        // Make sure to start with a new database
        db = new DataBase();
        db.startUp();
        courseModel = new CoursesModel();
        course1 = new Course("CMPT370", "Software Engineering", "Kevin");
        course2 = new Course("CMPT360", "Algorithms","Mondal");
        course3 = new Course("CMPT340", "Programming Paradigms", "Nadeem");
        courseModel.insertCourse(course1);
        courseModel.insertCourse(course2);
        courseModel.insertCourse(course3);
        assessment1 = new Assessment("A1", "CMPT340", 35, 1,1,1,
                " ", 10);
        assessment2 = new Assessment("A2", "CMPT340", 75, 1,1,1,
                " ", 10);
        assessment3 = new Assessment("A3", "CMPT340", 87, 1,1,1,
                " ", 10);
        assessment4 = new Assessment("A4", "CMPT340", 88, 1,1,1,
                " ", 10);
        assessment5 = new Assessment("A5", "CMPT340", 60, 1,1,1,
                " ", 10);
        assessment6 = new Assessment("final", "CMPT340", 55, 1,1,1,
                " ", 50);
        assessment7 = new Assessment("A1", "CMPT360", 60, 1,1,1,
                " ", 15);
        assessment8 = new Assessment("A2", "CMPT360", 65, 1,1,1,
                " ", 25);
        assessment9 = new Assessment("A1", "CMPT370", 100, 1,1,1,
                " ", 50);
        assessment10 = new Assessment("A2", "CMPT370", 100, 1,1,1,
                " ", 25);
        courseModel.insertAssessment(assessment1);
        courseModel.insertAssessment(assessment2);
        courseModel.insertAssessment(assessment3);
        courseModel.insertAssessment(assessment4);
        courseModel.insertAssessment(assessment5);
        courseModel.insertAssessment(assessment6);
        courseModel.insertAssessment(assessment7);
        courseModel.insertAssessment(assessment8);
        courseModel.insertAssessment(assessment9);
        courseModel.insertAssessment(assessment10);
        // Testing to create a lot of assignments so we have to scroll through them
        Course course4 = new Course("MATH364", "Number Theory", "Cam");
        //ArrayList<Assessment> lotsOfAssessments = new ArrayList<>();
        courseModel.insertCourse(course4);
        for (int i = 0 ; i < 50; i++) {
            String title = "" + i;
            Assessment assessment = new Assessment(title, "MATH364", 1,1,1,1,
            " ", 1);
            courseModel.insertAssessment(assessment);
        }
//        for (for i = ) {
//            courseModel.insertAssessment(lotsOfAssessments.get(i));
//        }
    }

    @Test
    public void exampleTest(){
        assertEquals(4, 2 + 2);
    }

    @Test
    public void insertCoursesTest() {
        ArrayList<Course> coursesDB;
        coursesDB = courseModel.getCoursesFromDB();
        assertTrue(course1.equalsByField(coursesDB.get(0)));
        assertTrue(course2.equalsByField(coursesDB.get(1)));
        assertTrue(course3.equalsByField(coursesDB.get(2)));
    }

    @Test
    public void insertAssessmentsTest() {
        ArrayList<Assessment> assessmentsDB;
        assessmentsDB = courseModel.getSpecificCourseAssessmentList("CMPT340");
        assertTrue(assessment1.equalsByField(assessmentsDB.get(0)));
        assertTrue(assessment2.equalsByField(assessmentsDB.get(1)));
        assertTrue(assessment3.equalsByField(assessmentsDB.get(2)));
        assertTrue(assessment4.equalsByField(assessmentsDB.get(3)));
        assertTrue(assessment5.equalsByField(assessmentsDB.get(4)));
        assertTrue(assessment6.equalsByField(assessmentsDB.get(5)));

        assessmentsDB = courseModel.getSpecificCourseAssessmentList("CMPT360");
        assertTrue(assessment7.equalsByField(assessmentsDB.get(0)));
        assertTrue(assessment8.equalsByField(assessmentsDB.get(1)));

        assessmentsDB = courseModel.getSpecificCourseAssessmentList("CMPT370");
        assertTrue(assessment9.equalsByField(assessmentsDB.get(0)));
        assertTrue(assessment10.equalsByField(assessmentsDB.get(1)));
    }

    @Test
    public void gradesTest(){
        courseModel.setSelectedCourse("CMPT340");
        courseModel.updateAssessmentList();
        assertEquals(62.0, courseModel.getAverageGrade(), 0.5);
        assertEquals(61.7, courseModel.getMinimumGrade(), 0.5);

        courseModel.setSelectedCourse("CMPT360");
        courseModel.updateAssessmentList();
        assertEquals(63.1, courseModel.getAverageGrade(), 0.5);
        assertEquals(25.25, courseModel.getMinimumGrade(), 0.5);

        courseModel.setSelectedCourse("CMPT370");
        courseModel.updateAssessmentList();
        assertEquals(100, courseModel.getAverageGrade(), 0.5);
        assertEquals(75.0, courseModel.getMinimumGrade(), 0.5);

    }


}
