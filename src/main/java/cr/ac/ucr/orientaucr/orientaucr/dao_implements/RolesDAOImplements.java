/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.orientaucr.orientaucr.dao_implements;

import cr.ac.ucr.orientaucr.orientaucr.connection.ConnectionDB;
import cr.ac.ucr.orientaucr.orientaucr.dao.RolesDAO;
import cr.ac.ucr.orientaucr.orientaucr.domain.Characteristic;
import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.domain.Test;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author carlo
 */
public class RolesDAOImplements implements RolesDAO{

    @Override
    public LinkedList<Roles> getAll(String search) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   @Override
public LinkedList<Roles> getAll() {
    LinkedList<Roles> list = new LinkedList<>();
    Map<String, Roles> map = new HashMap<>();

    try (Connection cn = ConnectionDB.getConnection();
         CallableStatement cs = cn.prepareCall("CALL sp_get_roles_permissions();");
         ResultSet rs = cs.executeQuery()) {

        while (rs.next()) {
            String rol_id = rs.getString(1);
            String rol_name = rs.getString(2);
            String permission_id = rs.getString(3);
            String permission_name = rs.getString(4);
            String permission_description = rs.getString(5);

            Roles rol = map.get(rol_id);
            if (rol == null) {
                rol = new Roles();
                rol.setRol_id(rol_id);
                rol.setRol_name(rol_name);  // o setRoleName según tu método
                rol.setPermissions(new LinkedList<>());
                map.put(rol_id, rol);
            }

            Permission p = new Permission();
            p.setPermission_id(permission_id);
            p.setPermission_name(permission_name);
            p.setPermission_description(permission_description);

            rol.getPermissions().add(p);
        }

        list.addAll(map.values());

    } catch (SQLException e) {
        System.out.println("Error Test getAll: " + e.getMessage());
    }

    return list;
}


    @Override
    public void add(Roles R) {
  try {
            R.setRol_id(UUID.randomUUID().toString());
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("CALL sp_add_role(?, ?)");
            cs.setString(1, R.getRol_id());
            cs.setString(2, R.getRol_name());
            cs.executeUpdate();

            for (Permission P : R.getPermissions()) {
                CallableStatement csChar = cn.prepareCall("CALL sp_assign_permission_to_role(?, ?)");
                csChar.setString(1, R.getRol_id());
                csChar.setString(2, P.getPermission_id());
                csChar.executeUpdate();
                csChar.close();
            }

            cs.close();
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error Test add: " + e.getMessage());
        }    
    }

    @Override
    public void update(Roles t) {
 try {
            
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("CALL sp_update_role(?, ?)");
            cs.setString(1, t.getRol_id());
            cs.setString(2, t.getRol_name());
            cs.executeUpdate();
            cs.close();

            CallableStatement deleteChar = cn.prepareCall("CALL sp_delete_permissions_from_role(?)");
            deleteChar.setString(1, t.getRol_id());
            deleteChar.executeUpdate();
            deleteChar.close();

            for (Permission p : t.getPermissions()) {
                CallableStatement csChar = cn.prepareCall("CALL sp_assign_permission_to_role(?, ?)");
                csChar.setString(1, t.getRol_id());
                csChar.setString(2, p.getPermission_id());
                csChar.executeUpdate();
                csChar.close();
            }
            System.out.println("Pase por aqui");

            cn.close();

        } catch (SQLException e) {
            System.out.println("Error Test update: " + e.getMessage());
        }
 
     }

    @Override
    public void deleteById(String id) {
       try {

            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("CALL sp_delete_role(?)");
            cs.setString(1, id);
            cs.executeUpdate();
            cs.close();
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error Test deleteById: " + e.getMessage());
        }
    }

    @Override
    public Roles findById(String id) {
    Roles rol = null;

        try {

            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("CALL sp_get_role_by_id(?)");
            cs.setString(1, id);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                if (rol == null) {
                    rol = new Roles();
                    rol.setRol_id(rs.getString(1));
                    rol.setRol_name(rs.getString(2));
                    rol.setPermissions(new LinkedList<>());
                }

                Permission p = new Permission();
                p.setPermission_id(rs.getString(3));
                p.setPermission_name(rs.getString(4));
                p.setPermission_description(rs.getString(5));

                rol.getPermissions().add(p);
            }

            rs.close();
            cs.close();
            cn.close();
            System.out.println("Error Test findById");
        } catch (SQLException e) {
            System.out.println("Error Test findById: " + e.getMessage());
        }

        return rol;
    }

    @Override
    public LinkedList<Permission> getAllPermissions() {
       LinkedList<Permission> list = new LinkedList<>();

        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("CALL sp_get_all_permissions()");
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Permission P = new Permission();
                P.setPermission_id(rs.getString(1));
                P.setPermission_name(rs.getString(2));
                P.setPermission_description(rs.getString(3));
                list.add(P);
            }

            rs.close();
            cs.close();
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error Chracteristic getAll: " + e.getMessage());
        }

        return list;
    }
}