/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.ucr.orientaucr.orientaucr.dao;

import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import java.util.LinkedList;

/**
 *
 * @author carlo
 */
public interface RolesDAO extends CRUD<Roles>{
     public LinkedList<Permission> getAllPermissions();
}
