package cr.ac.ucr.orientaucr.orientaucr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Entity
@Table(name="career")
public class Career {
    
    @Id
    @Column(name = "career_id", length = 36)
    private String career_id;
    @Column(name = "career_name", nullable = false, length = 100)
    private String career_name;
    @Column(name = "career_description", columnDefinition = "TEXT")
    private String career_description;
    @Column(name = "career_duration_years", nullable = false)
    private int career_duration_years;
    @ManyToMany
    @JoinTable(
        name = "career_characteristics", 
        joinColumns = @JoinColumn(name = "career_id"), 
        inverseJoinColumns = @JoinColumn(name = "characteristics_id") 
    )
    @JsonIgnoreProperties("careers")
    private Set<Characteristic> characteristics = new HashSet<>();

    public Career() {}

    public Career(String career_id, String career_name, String career_description, int career_duration_years) {
        this.career_id = career_id;
        this.career_name = career_name;
        this.career_description = career_description;
        this.career_duration_years = career_duration_years;
    }

    public String getCareer_id() {
        return career_id;
    }

    public void setCareer_id(String career_id) {
        this.career_id = career_id;
    }

    public String getCareer_name() {
        return career_name;
    }

    public void setCareer_name(String career_name) {
        this.career_name = career_name;
    }

    public String getCareer_description() {
        return career_description;
    }

    public void setCareer_description(String career_description) {
        this.career_description = career_description;
    }

    public int getCareer_duration_years() {
        return career_duration_years;
    }

    public void setCareer_duration_years(int career_duration_years) {
        this.career_duration_years = career_duration_years;
    }

    public Set<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(Set<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }

}