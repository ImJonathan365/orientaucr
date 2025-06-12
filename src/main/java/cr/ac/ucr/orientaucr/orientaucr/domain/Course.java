package cr.ac.ucr.orientaucr.orientaucr.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "course")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "courseId"
)
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

    private int courseSemester = 0;

    @ManyToMany
    @JoinTable(
            name = "course_prerequisite",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "prerequisite_id")
    )
    @JsonIgnore
    private Set<Course> prerequisites = new HashSet<>();

    @ManyToMany(mappedBy = "prerequisites")
    @JsonIgnore
    private Set<Course> dependentCourses = new HashSet<>();

    @JsonSetter("prerequisites")
    public void setPrerequisitesFromIds(List<String> prerequisiteIds) {
        if (prerequisiteIds == null || prerequisiteIds.isEmpty()) {
            this.prerequisites = new HashSet<>();
        } else {
            this.prerequisites = prerequisiteIds.stream()
                    .map(id -> {
                        Course c = new Course();
                        c.setCourseId(id);  
                        return c;
                    })
                    .collect(Collectors.toSet());
        }
    }

    @JsonGetter("prerequisites")
    public List<String> getPrerequisitesIds() {
        if (prerequisites == null) {
            return new ArrayList<>();
        }
        return prerequisites.stream()
                .map(Course::getCourseId)
                .collect(Collectors.toList());
    }

    public Course() {
    }

    public Course(String courseId, String courseCode, int courseCredits, String courseName, String courseDescription) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseCredits = courseCredits;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
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

    public int getCourseSemester() {
        return courseSemester;
    }

    public void setCourseSemester(int courseSemester) {
        this.courseSemester = courseSemester;
    }

    public Set<Course> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(Set<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public Set<Course> getDependentCourses() {
        return dependentCourses;
    }

    public void setDependentCourses(Set<Course> dependentCourses) {
        this.dependentCourses = dependentCourses;
    }

}
