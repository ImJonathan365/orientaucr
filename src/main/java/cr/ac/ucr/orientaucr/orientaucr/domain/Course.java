
package cr.ac.ucr.orientaucr.orientaucr.domain;

public class Course {
    
    private String course_id;
    private String course_code;
    private int course_credits;
    private String course_name;
    private String course_description;

    public Course() {}

    public Course(String course_id, String course_code, int course_credits, String course_name, String course_description) {
        this.course_id = course_id;
        this.course_code = course_code;
        this.course_credits = course_credits;
        this.course_name = course_name;
        this.course_description = course_description;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public int getCourse_credits() {
        return course_credits;
    }

    public void setCourse_credits(int course_credits) {
        this.course_credits = course_credits;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_description() {
        return course_description;
    }

    public void setCourse_description(String course_description) {
        this.course_description = course_description;
    }
    
    
}
