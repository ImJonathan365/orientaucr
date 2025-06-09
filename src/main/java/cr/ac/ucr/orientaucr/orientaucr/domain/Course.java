package cr.ac.ucr.orientaucr.orientaucr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course {
    
    @Id
    @Column(name = "course_id", columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String courseId;
    
    @Column(name = "course_code", nullable = false, length = 10)
    private String courseCode;
    
    @Column(name = "course_credits", nullable = false)
    private int courseCredits;
    
    @Column(name = "course_name", nullable = false, length = 100)
    private String courseName;
    
    @Column(name = "course_description", columnDefinition = "TEXT")
    private String courseDescription;
    
    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurriculumCourse> curriculumCourses = new HashSet<>();

    public Course() {}

    public Course(String courseCode, int courseCredits, String courseName, String courseDescription) {
        this.courseCode = courseCode;
        this.courseCredits = courseCredits;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    // Getters y Setters
    public String getCourseId() {
        return courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getCourseCredits() {
        return courseCredits;
    }

    public void setCourseCredits(int courseCredits) {
        this.courseCredits = courseCredits;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public Set<CurriculumCourse> getCurriculumCourses() {
        return curriculumCourses;
    }

    public void setCurriculumCourses(Set<CurriculumCourse> curriculumCourses) {
        this.curriculumCourses = curriculumCourses;
    }
}