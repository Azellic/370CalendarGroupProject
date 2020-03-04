package Model;

public class Course {
    protected String title;

    //private assessments;

    public Course(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
