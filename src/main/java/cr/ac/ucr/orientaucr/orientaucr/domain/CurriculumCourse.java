
package cr.ac.ucr.orientaucr.orientaucr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;


@Entity
@Table(name = "curriculum_courses")
public class CurriculumCourse {
    @EmbeddedId  // PK compuesta embebida
    private CurriculumCourseKey id;

    @ManyToOne
    @MapsId("curriculaId")  // Relaciona con curriculaId de CurriculumCourseKey
    @JoinColumn(name = "curricula_id")
    @JsonIgnore
    private Curricula curricula;

    @ManyToOne
    @MapsId("courseId")     // Relaciona con courseId de CurriculumCourseKey
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;

    @Column(name = "course_semester", nullable = false)
    private int courseSemester;

    public CurriculumCourse(CurriculumCourseKey id, Curricula curricula, Course course, int courseSemester) {
        this.id = id;
        this.curricula = curricula;
        this.course = course;
        this.courseSemester = courseSemester;
    }

    public CurriculumCourseKey getId() {
        return id;
    }

    public void setId(CurriculumCourseKey id) {
        this.id = id;
    }

    public Curricula getCurricula() {
        return curricula;
    }

    public void setCurricula(Curricula curricula) {
        this.curricula = curricula;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getCourseSemester() {
        return courseSemester;
    }

    public void setCourseSemester(int courseSemester) {
        this.courseSemester = courseSemester;
    }
    
    
}
