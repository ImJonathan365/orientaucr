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
    private String characteristicsId;
    @Column(name = "characteristics_name", nullable = false, length = 100)
    private String characteristicsName;
    @Column(name = "characteristics_description", columnDefinition = "TEXT")
    private String characteristicsDescription;

    @ManyToMany(mappedBy = "characteristics")
    @JsonIgnore
    private Set<Career> careers = new HashSet<>();

    @ManyToMany(mappedBy = "characteristics")
    @JsonIgnore
    private List<Test> tests = new ArrayList<>();

    public Characteristic() {
    }

    public Characteristic(String characteristicsId, String characteristicsName, String characteristicsDescription) {
        this.characteristicsId = characteristicsId;
        this.characteristicsName = characteristicsName;
        this.characteristicsDescription = characteristicsDescription;
    }

    public String getCharacteristicsId() {
        return characteristicsId;
    }

    public void setCharacteristicsId(String characteristicsId) {
        this.characteristicsId = characteristicsId;
    }

    public String getCharacteristicsName() {
        return characteristicsName;
    }

    public void setCharacteristicsName(String characteristicsName) {
        this.characteristicsName = characteristicsName;
    }

    public String getCharacteristicsDescription() {
        return characteristicsDescription;
    }

    public void setCharacteristicsDescription(String characteristicsDescription) {
        this.characteristicsDescription = characteristicsDescription;
    }

    public Set<Career> getCareers() {
        return careers;
    }

    public void setCareers(Set<Career> careers) {
        this.careers = careers;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    

}
