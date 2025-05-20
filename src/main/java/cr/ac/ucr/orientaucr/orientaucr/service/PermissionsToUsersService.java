/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.orientaucr.orientaucr.service;

import cr.ac.ucr.orientaucr.orientaucr.dao_implements.PermissionToUsersDAOImplements;
import cr.ac.ucr.orientaucr.orientaucr.domain.PermissionToUsers;
import java.util.LinkedList;

/**
 *
 * @author carlo
 */
public class PermissionsToUsersService  {
      private static  final PermissionToUsersDAOImplements  data= new PermissionToUsersDAOImplements();
     public static LinkedList<PermissionToUsers>getAllRolesOrPermissions(String rol_id){
     return data.getAllRolesOrPermissions(rol_id);
     }
    public static LinkedList<PermissionToUsers>getAllPermissionOfUser(String user_id){
    return data.getAllPermissionOfUser(user_id);
    }
    public static void add(String user_id,String permission_id){
    data.add(user_id, permission_id);
    }
   public static void delete(String user_id,String permission_id){
     data.deleteById(user_id, permission_id);
   }
   public static void update (String user_id,String rol_id){
   data.update(user_id, rol_id);
   }
    public static String findById (String user_id){
   return data.FindById(user_id);
   }
}
