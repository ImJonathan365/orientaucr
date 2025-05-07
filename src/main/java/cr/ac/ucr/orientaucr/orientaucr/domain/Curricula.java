
package cr.ac.ucr.orientaucr.orientaucr.domain;

public class Curricula {
    
    private String curricula_id;
    private String career_id;

    public Curricula() {}
    
    public Curricula(String curricula_id, String career_id) {
        this.curricula_id = curricula_id;
        this.career_id = career_id;
    }

    public String getCurricula_id() {
        return curricula_id;
    }

    public void setCurricula_id(String curricula_id) {
        this.curricula_id = curricula_id;
    }

    public String getCareer_id() {
        return career_id;
    }

    public void setCareer_id(String career_id) {
        this.career_id = career_id;
    }
    
    
}
