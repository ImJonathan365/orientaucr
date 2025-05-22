package cr.ac.ucr.orientaucr.orientaucr.domain;

import java.util.LinkedList;

public class Roles {
    
    private String rol_id;
    private String rol_name;
    private LinkedList<Permission> permissions;

    public Roles(String rol_id, String rol_name, LinkedList<Permission> permissions) {
        this.rol_id = rol_id;
        this.rol_name = rol_name;
        this.permissions = permissions;
    }

    public Roles() {
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

   

    public LinkedList<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(LinkedList<Permission> permissions) {
        this.permissions = permissions;
    }
    
}