package cr.ac.ucr.orientaucr.orientaucr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "characteristics")
public class Characteristic {

    @Id
    @Column(name = "characteristics_id", length = 36)
    private String characteristics_id;
    
    @Column(name = "characteristics_name", nullable = false, length = 100)
    private String characteristics_name;
    
    @Column(name = "characteristics_description", columnDefinition = "TEXT")
    private String characteristics_description;
    
    @ManyToMany(mappedBy = "characteristics")
    @JsonIgnore
    private Set<Career> careers = new HashSet<>();

    @ManyToMany(mappedBy = "characteristics")
    @JsonIgnore
    private List<Test> tests = new ArrayList<>();

    public Characteristic() {
    }

    public Characteristic(String characteristics_id, String characteristics_name, String characteristics_description) {
        this.characteristics_id = characteristics_id;
        this.characteristics_name = characteristics_name;
        this.characteristics_description = characteristics_description;
    }

    public String getCharacteristics_id() {
        return characteristics_id;
    }

    public void setCharacteristics_id(String characteristics_id) {
        this.characteristics_id = characteristics_id;
    }

    public String getCharacteristics_name() {
        return characteristics_name;
    }

    public void setCharacteristics_name(String characteristics_name) {
        this.characteristics_name = characteristics_name;
    }

    public String getCharacteristics_description() {
        return characteristics_description;
    }

    public void setCharacteristics_description(String characteristics_description) {
        this.characteristics_description = characteristics_description;
    }

    public Set<Career> getCareers() {
        return careers;
    }

    public void setCareers(Set<Career> careers) {
        this.careers = careers;
    }

}
