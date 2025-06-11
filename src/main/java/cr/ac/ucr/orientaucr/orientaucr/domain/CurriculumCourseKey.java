
package cr.ac.ucr.orientaucr.orientaucr.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable  // Indica que esta clase se incrustará en otra
public class CurriculumCourseKey implements Serializable {
    @Column(name = "curricula_id")
    private String curriculaId;

    @Column(name = "course_id")
    private String courseId;

    public CurriculumCourseKey() {}
    
    
    public CurriculumCourseKey(String curriculaId, String courseId) {
        this.curriculaId = curriculaId;
        this.courseId = courseId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.curriculaId);
        hash = 47 * hash + Objects.hashCode(this.courseId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CurriculumCourseKey other = (CurriculumCourseKey) obj;
        if (!Objects.equals(this.curriculaId, other.curriculaId)) {
            return false;
        }
        return Objects.equals(this.courseId, other.courseId);
    }

    public String getCurriculaId() {
        return curriculaId;
    }

    public void setCurriculaId(String curriculaId) {
        this.curriculaId = curriculaId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    

    
}