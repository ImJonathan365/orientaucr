/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.ucr.orientaucr.orientaucr.dao;

import java.util.LinkedList;


public interface CRUD_ROLES <T> {

    public LinkedList<T> getAllRolesOrPermissions(String rol_id);
        
    public LinkedList<T> getAllPermissionOfUser(String user_id);
    
     public void getUserRol(String user_id);

    public void add(String user_id,String permission_id);

    public void update(String user_id,String rol_id);

    public void deleteById(String user_id,String permission_id);

   
}
