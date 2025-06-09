
package cr.ac.ucr.orientaucr.orientaucr.domain;


public class Campus {
    
    private String campusId;
    private String campusName;
    private String campusLocation;
    private String campusDescription;

    public Campus() {}

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
