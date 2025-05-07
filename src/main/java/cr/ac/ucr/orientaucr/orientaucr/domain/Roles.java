/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.orientaucr.orientaucr.domain;

/**
 *
 * @author carlo
 */
public class Roles {
    private String rol_name;
    private String rol_id;

    // Constructor vacío
    public Roles() {}

    // Constructor con parámetros
    public Roles(String rol_id, String rol_name) {
        this.rol_id = rol_id;
        this.rol_name = rol_name;
    }

    // Getters y setters
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



