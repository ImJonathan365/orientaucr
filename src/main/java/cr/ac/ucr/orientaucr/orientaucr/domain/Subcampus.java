
package cr.ac.ucr.orientaucr.orientaucr.domain;


public class Subcampus {
    
    private String subcampusId;
    private String subcampusName;
    private String subcampusDescription;
    private String subcampusLocation;
    private String campusId;

    public Subcampus() {}

    public Subcampus(String subcampusId, String subcampusName, String subcampusDescription, String subcampusLocation, String campusId) {
        this.subcampusId = subcampusId;
        this.subcampusName = subcampusName;
        this.subcampusDescription = subcampusDescription;
        this.subcampusLocation = subcampusLocation;
        this.campusId = campusId;
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

    public String getCampusId() {
        return campusId;
    }

    public void setCampusId(String campusId) {
        this.campusId = campusId;
    }

    

}
