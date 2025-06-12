package cr.ac.ucr.orientaucr.orientaucr.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SubcampusId implements Serializable {

    @Column(name = "subcampus_id", columnDefinition = "CHAR(36)")
    private String subcampusId;

    @Column(name = "campus_id", columnDefinition = "CHAR(36)")
    private String campusId;

    public SubcampusId() {}

    public SubcampusId(String subcampusId, String campusId) {
        this.subcampusId = subcampusId;
        this.campusId = campusId;
    }

    public String getSubcampusId() {
        return subcampusId;
    }

    public void setSubcampusId(String subcampusId) {
        this.subcampusId = subcampusId;
    }

    public String getCampusId() {
        return campusId;
    }

    public void setCampusId(String campusId) {
        this.campusId = campusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubcampusId)) return false;
        SubcampusId that = (SubcampusId) o;
        return Objects.equals(subcampusId, that.subcampusId) &&
               Objects.equals(campusId, that.campusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subcampusId, campusId);
    }
}
