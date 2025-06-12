package cr.ac.ucr.orientaucr.orientaucr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "campus")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Campus {

    @Id
    @Column(name = "campus_id", columnDefinition = "CHAR(36)")
    private String campusId;

    @Column(name = "campus_name", nullable = false, length = 100)
    private String campusName;

    @Column(name = "campus_location", columnDefinition = "TINYTEXT")
    private String campusLocation;

    @Column(name = "campus_description", columnDefinition = "TEXT")
    private String campusDescription;

    public Campus() {
        this.campusId = UUID.randomUUID().toString(); // autogenera si no se recibe desde BD
    }

    public Campus(String campusId, String campusName, String campusLocation, String campusDescription) {
        this.campusId = campusId;
        this.campusName = campusName;
        this.campusLocation = campusLocation;
        this.campusDescription = campusDescription;
    }

    public String getCampusId() {
        return campusId;
    }

    public void setCampusId(String campusId) {
        this.campusId = campusId;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public String getCampusLocation() {
        return campusLocation;
    }

    public void setCampusLocation(String campusLocation) {
        this.campusLocation = campusLocation;
    }

    public String getCampusDescription() {
        return campusDescription;
    }

    public void setCampusDescription(String campusDescription) {
        this.campusDescription = campusDescription;
    }
}
