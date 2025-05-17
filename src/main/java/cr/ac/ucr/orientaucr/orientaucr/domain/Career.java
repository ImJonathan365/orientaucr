
package cr.ac.ucr.orientaucr.orientaucr.domain;

import java.util.LinkedList;


public class Career {
    
    private String career_id;
    private String career_name;
    private String career_description;
    private int career_duration_years;
    private LinkedList<Characteristic> characteristicList;

    public Career() {}

    public Career(String career_id, String career_name, String career_description, int career_duration_years, LinkedList<Characteristic> characteristicList) {
        this.career_id = career_id;
        this.career_name = career_name;
        this.career_description = career_description;
        this.career_duration_years = career_duration_years;
        this.characteristicList = characteristicList;
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

    public LinkedList<Characteristic> getCharacteristicList() {
        return characteristicList;
    }

    public void setCharacteristicList(LinkedList<Characteristic> characteristicList) {
        this.characteristicList = characteristicList;
    }

    
    
    
}
