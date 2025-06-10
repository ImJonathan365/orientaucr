package cr.ac.ucr.orientaucr.orientaucr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="career")
public class Career {
    
    @Id
    @Column(name = "career_id", length = 36)
    private String careerId;
    @Column(name = "career_name", nullable = false, length = 100)
    private String careerName;
    @Column(name = "career_description", columnDefinition = "TEXT")
    private String careerDescription;
    @Column(name = "career_duration_years", nullable = false)
    private int careerDurationYears;
    @ManyToMany
    @JoinTable(
        name = "career_characteristics", 
        joinColumns = @JoinColumn(name = "career_id"), 
        inverseJoinColumns = @JoinColumn(name = "characteristics_id") 
    )
    @JsonIgnoreProperties("careers")
    private Set<Characteristic> characteristics = new HashSet<>();
    
    @OneToOne(mappedBy = "career", cascade = CascadeType.REMOVE)
    private Curricula curricula;

    public Career() {}

    public Career(String careerId, String careerName, String careerDescription, int careerDurationYears, Curricula curricula) {
        this.careerId = careerId;
        this.careerName = careerName;
        this.careerDescription = careerDescription;
        this.careerDurationYears = careerDurationYears;
        this.curricula = curricula;
    }

    public String getCareerId() {
        return careerId;
    }

    public void setCareerId(String careerId) {
        this.careerId = careerId;
    }

    public String getCareerName() {
        return careerName;
    }

    public void setCareerName(String careerName) {
        this.careerName = careerName;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public int getCareerDurationYears() {
        return careerDurationYears;
    }

    public void setCareerDurationYears(int careerDurationYears) {
        this.careerDurationYears = careerDurationYears;
    }

    public Set<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(Set<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }

    public Curricula getCurricula() {
        return curricula;
    }

    public void setCurricula(Curricula curricula) {
        this.curricula = curricula;
    }

    

    

}