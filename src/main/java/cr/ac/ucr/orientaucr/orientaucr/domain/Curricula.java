package cr.ac.ucr.orientaucr.orientaucr.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "curricula")
public class Curricula {

    @Id
    @Column(name = "curricula_id", length = 36)
    private String curriculaId;  // Cambiado a camelCase para consistencia

    @OneToMany(mappedBy = "curricula", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurriculumCourse> courses = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "career_id")
    private Career career;

    public Curricula() {
    }

    public Curricula(String curriculaId, Career career) {
        this.curriculaId = curriculaId;
        this.career = career;
    }

    public String getCurriculaId() {
        return curriculaId;
    }

    public void setCurriculaId(String curriculaId) {
        this.curriculaId = curriculaId;
    }

    public Set<CurriculumCourse> getCourses() {
        return courses;
    }

    public void setCourses(Set<CurriculumCourse> courses) {
        this.courses = courses;
    }

    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    

}
