/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.orientaucr.orientaucr.service;

import cr.ac.ucr.orientaucr.orientaucr.dao_implements.RolesDAOImplements;
import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import java.util.LinkedList;

/**
 *
 * @author carlo
 */
public class RolesService {
     private static  final RolesDAOImplements  data= new RolesDAOImplements();
    
    public static LinkedList<Roles> getAllRoles(){
    return data.getAll();
    }
   public static LinkedList<Roles> getAllRoles(String id){
    return data.getAll(id);
    }
    public static void add(Roles t){
        data.add(t);
    }
    public static void DeleteById(String y){
        data.deleteById(y);
    
   }
    public static void updateUser( Roles t){
       data.update(t);
    }
    public static Roles FindUserByIde(String t){
        return data.findById(t);
    }
}
