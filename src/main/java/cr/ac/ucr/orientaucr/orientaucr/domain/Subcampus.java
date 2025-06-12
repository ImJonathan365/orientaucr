package cr.ac.ucr.orientaucr.orientaucr.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "subcampus")
public class Subcampus {

    @Id
    @Column(name = "subcampus_id", columnDefinition = "CHAR(36)")
    private String subcampusId;

    @Column(name = "subcampus_name", nullable = false, length = 100)
    private String subcampusName;

    @Column(name = "subcampus_description", columnDefinition = "TEXT")
    private String subcampusDescription;

    @Column(name = "subcampus_location", columnDefinition = "TINYTEXT")
    private String subcampusLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id", nullable = false)
    private Campus campus;

    public Subcampus() {
        this.subcampusId = UUID.randomUUID().toString();
    }

    public Subcampus(String name, String description, String location, Campus campus) {
        this.subcampusId = UUID.randomUUID().toString();
        this.subcampusName = name;
        this.subcampusDescription = description;
        this.subcampusLocation = location;
        this.campus = campus;
    }

    public String getSubcampusId() {
        return subcampusId;
    }

    public void setSubcampusId(String subcampusId) {
        this.subcampusId = subcampusId;
    }

    public String getSubcampusName() {
        return subcampusName;
    }

    public void setSubcampusName(String subcampusName) {
        this.subcampusName = subcampusName;
    }

    public String getSubcampusDescription() {
        return subcampusDescription;
    }

    public void setSubcampusDescription(String subcampusDescription) {
        this.subcampusDescription = subcampusDescription;
    }

    public String getSubcampusLocation() {
        return subcampusLocation;
    }

    public void setSubcampusLocation(String subcampusLocation) {
        this.subcampusLocation = subcampusLocation;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }
}
