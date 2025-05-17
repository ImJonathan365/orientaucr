/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.orientaucr.orientaucr.domain;

/**
 *
 * @author carlo
 */
public class PermissionToUsers {
    private String user_id;
    private String permission_id;
    private String rol_id;
    private String rol_name;
    private String permission_name;
    private String descripcion;
    public PermissionToUsers() {
    }
    public PermissionToUsers(String user_id, String permission_id) {
        this.user_id = user_id;
        this.permission_id = permission_id;
    }

    public PermissionToUsers(String user_id, String permission_id, String rol_id) {
        this.user_id = user_id;
        this.permission_id = permission_id;
        this.rol_id = rol_id;
    }

    public PermissionToUsers(String user_id, String permission_id, String rol_id, String rol_name, String permission_name, String descripcion) {
        this.user_id = user_id;
        this.permission_id = permission_id;
        this.rol_id = rol_id;
        this.rol_name = rol_name;
        this.permission_name = permission_name;
        this.descripcion = descripcion;
    }

    public String getRol_name() {
        return rol_name;
    }

    public String getRol_id() {
        return rol_id;
    }

    public void setRol_id(String rol_id) {
        this.rol_id = rol_id;
    }

    public void setRol_name(String rol_name) {
        this.rol_name = rol_name;
    }

    public String getPermission_name() {
        return permission_name;
    }

    public void setPermission_name(String permission_name) {
        this.permission_name = permission_name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPermission_id() {
        return permission_id;
    }

    public void setPermission_id(String permission_id) {
        this.permission_id = permission_id;
    }
  
}

