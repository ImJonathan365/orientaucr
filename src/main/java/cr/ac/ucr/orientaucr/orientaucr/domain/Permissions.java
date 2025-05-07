/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.orientaucr.orientaucr.domain;

/**
 *
 * @author carlo
 */
public class Permissions {
    private String permission_id;
    private String permission_name;

    public Permissions() {
    }

    public Permissions(String permission_id, String permission_name) {
        this.permission_id = permission_id;
        this.permission_name = permission_name;
    }

    public String getPermission_id() {
        return permission_id;
    }

    public void setPermission_id(String permission_id) {
        this.permission_id = permission_id;
    }

    public String getPermission_name() {
        return permission_name;
    }

    public void setPermission_name(String permission_name) {
        this.permission_name = permission_name;
    }
    
}
