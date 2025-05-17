
package cr.ac.ucr.orientaucr.orientaucr.domain;


public class Campus {
    
    private String campus_id;
    private String campus_name;
    private String campus_location;
    private String campus_description;

    public Campus() {}

    
    public Campus(String campus_id, String campus_name, String campus_location, String campus_description) {
        this.campus_id = campus_id;
        this.campus_name = campus_name;
        this.campus_location = campus_location;
        this.campus_description = campus_description;
    }

    public String getCampus_id() {
        return campus_id;
    }

    public void setCampus_id(String campus_id) {
        this.campus_id = campus_id;
    }

    public String getCampus_name() {
        return campus_name;
    }

    public void setCampus_name(String campus_name) {
        this.campus_name = campus_name;
    }

    public String getCampus_location() {
        return campus_location;
    }

    public void setCampus_location(String campus_location) {
        this.campus_location = campus_location;
    }

    public String getCampus_description() {
        return campus_description;
    }

    public void setCampus_description(String campus_description) {
        this.campus_description = campus_description;
    }
    
    
}
