package cr.ac.ucr.orientaucr.orientaucr.domain;

public class Characteristic {
    
    private String characteristics_id;
    private String characteristics_name;
    private String characteristics_description;

    public Characteristic() {}

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
    
}
