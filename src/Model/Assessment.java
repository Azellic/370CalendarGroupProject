package Model;

import java.util.Date;

public class Assessment {

    private String title;
    private Course course;
    private int mark;
    private int day;
    private int month;
    private int year;
    private String description;
    private float weight;


    public Assessment(String title, Course course, int mark, int day, int month, int year,
                      String description, float weight){
        this.title = title;
        this.course = course;
        this.mark = mark;
        this.day = day;
        this.month = month;
        this.year = year;
        this.description = description;
        this.weight = weight;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "title='" + title + '\'' +
                ", course=" + course +
                ", mark=" + mark +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                '}';
    }
}
