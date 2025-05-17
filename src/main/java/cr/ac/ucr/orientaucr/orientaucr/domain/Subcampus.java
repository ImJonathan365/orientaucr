
package cr.ac.ucr.orientaucr.orientaucr.domain;


public class Subcampus {
    
    private String subcampus_id;
    private String subcampus_name;
    private String subcampus_description;
    private String subcampus_location;
    private String campus_id;

    public Subcampus() {}

    public Subcampus(String subcampus_id, String subcampus_name, String subcampus_description, String subcampus_location, String campus_id) {
        this.subcampus_id = subcampus_id;
        this.subcampus_name = subcampus_name;
        this.subcampus_description = subcampus_description;
        this.subcampus_location = subcampus_location;
        this.campus_id = campus_id;
    }

    public String getSubcampus_id() {
        return subcampus_id;
    }

    public void setSubcampus_id(String subcampus_id) {
        this.subcampus_id = subcampus_id;
    }

    public String getSubcampus_name() {
        return subcampus_name;
    }

    public void setSubcampus_name(String subcampus_name) {
        this.subcampus_name = subcampus_name;
    }

    public String getSubcampus_description() {
        return subcampus_description;
    }

    public void setSubcampus_description(String subcampus_description) {
        this.subcampus_description = subcampus_description;
    }

    public String getSubcampus_location() {
        return subcampus_location;
    }

    public void setSubcampus_location(String subcampus_location) {
        this.subcampus_location = subcampus_location;
    }

    public String getCampus_id() {
        return campus_id;
    }

    public void setCampus_id(String campus_id) {
        this.campus_id = campus_id;
    }
    
    
    
    
    
}
