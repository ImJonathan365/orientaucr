/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.orientaucr.orientaucr.dao_implements;

import cr.ac.ucr.orientaucr.orientaucr.connection.ConnectionDB;
import cr.ac.ucr.orientaucr.orientaucr.dao.RolesDAO;
import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author carlo
 */
public class RolesDAOImplements implements RolesDAO {

    @Override
    public LinkedList<Roles> getAll(String search) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public LinkedList<Roles> getAll() {
        LinkedList<Roles> list = new LinkedList<>();

        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("{CALL get_all_Roles()}");
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Roles Rol = new Roles();
                Rol.setRol_id(rs.getNString(1));
                Rol.setRol_name(rs.getString(2));
                list.add(Rol);
            }

        } catch (SQLException e) {
            System.err.println("Error al ejecutar cargar: " + e.getMessage());
        }

        System.out.println("Se cargó la lista");
        return list;
    }

    @Override
    public void add(Roles t) {
        String sql = "{CALL sp_insert_Roles(?)}"; 
        try (Connection cn = ConnectionDB.getConnection(); CallableStatement cs = cn.prepareCall(sql)) {
            cs.setString(1, t.getRol_name()); 
            int rowsAffected = cs.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Se insertó correctamente usando procedimiento");       
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar procedimiento: " + e.getMessage());
        }

    }

    @Override
    public void update(Roles t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(String i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Roles findById(String i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
