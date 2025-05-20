package cr.ac.ucr.orientaucr.orientaucr.domain;

public class Role {
    
    private String rol_id;
    private String rol_name;

    public Role() {
    }

    public Role(String rol_id, String rol_name) {
        this.rol_id = rol_id;
        this.rol_name = rol_name;
    }

    public String getRol_id() {
        return rol_id;
    }

    public void setRol_id(String rol_id) {
        this.rol_id = rol_id;
    }

    public String getRol_name() {
        return rol_name;
    }

    public void setRol_name(String rol_name) {
        this.rol_name = rol_name;
    }
    
}
