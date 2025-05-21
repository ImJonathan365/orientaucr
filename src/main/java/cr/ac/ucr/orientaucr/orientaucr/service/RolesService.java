/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.orientaucr.orientaucr.service;

import cr.ac.ucr.orientaucr.orientaucr.dao_implements.RolesDAOImplements;
import cr.ac.ucr.orientaucr.orientaucr.dao_implements.UserDAOImplements;
import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.domain.Test;
import java.util.LinkedList;

/**
 *
 * @author carlo
 */
public class RolesService {
     private static final RolesDAOImplements dataRoles = new RolesDAOImplements();
      public LinkedList<Roles> searchTest(String search){
        return dataRoles.getAll(search);
    }
    
    public LinkedList<Roles> getAllTest(){
        return dataRoles.getAll();
    }
    
    public void addTest(Roles r) {
        dataRoles.add(r);
    }
    
    public void updateTest(Roles t) {
        dataRoles.update(t);
    }
    
    public void deleteTestById(String id) {
        dataRoles.deleteById(id);
    }
    
    public Roles findTestById(String id) {
        return dataRoles.findById(id);
    }
}
