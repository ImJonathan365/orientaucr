package cr.ac.ucr.orientaucr.orientaucr.dao_implements;

import cr.ac.ucr.orientaucr.orientaucr.connection.ConnectionDB;
import cr.ac.ucr.orientaucr.orientaucr.dao.CareerDAO;
import cr.ac.ucr.orientaucr.orientaucr.domain.Career;
import cr.ac.ucr.orientaucr.orientaucr.domain.Characteristic;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;

public class CareerDAOImplements implements CareerDAO {

    @Override
    public LinkedList<Career> getAll(String search) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public LinkedList<Career> getAll() {
        LinkedList<Career> listCareer = new LinkedList<>();
        Map<String, Career> map = new HashMap<>();
        Connection cn = ConnectionDB.getConnection();
        String sql = "call `sp_get_all_careers`;";

        try {
            CallableStatement pstmt = cn.prepareCall(sql);
            ResultSet rs = pstmt.executeQuery();
            String career_id;
            String career_name;
            String career_description;
            int career_duration_years;
            String characteristics_id;
            String characteristics_name;
            String characteristics_description;
            while (rs.next()) {
                career_id = rs.getString(1);
                career_name = rs.getString(2);
                career_description = rs.getString(3);
                career_duration_years = rs.getInt(4);
                characteristics_id = rs.getString(5);
                characteristics_name = rs.getString(6);
                characteristics_description = rs.getString(7);
                Career career = map.get(career_id);

                if (career == null) {

                    career = new Career();
                    career.setCareer_id(career_id);
                    career.setCareer_name(career_name);
                    career.setCareer_description(career_description);
                    career.setCareer_duration_years(career_duration_years);
                    career.setCharacteristicList(new LinkedList<>());
                    map.put(career_id, career);
                }

                if (characteristics_id != null) {
                    Characteristic characteristic = new Characteristic();
                    characteristic.setCharacteristics_id(characteristics_id);
                    characteristic.setCharacteristics_name(characteristics_name);
                    characteristic.setCharacteristics_description(characteristics_description);

                    career.getCharacteristicList().add(characteristic);
                }
            }
            listCareer.addAll(map.values());
        } catch (SQLException ex) {
            System.err.println("Ocurrio un error en la consulta de base de datos:" + ex.getMessage());
        }
        return listCareer;
    }

    @Override
    public void add(Career t) {
        Connection cn = ConnectionDB.getConnection();
        String sql = "call `sp_add_career`(?, ?, ?, ?);";
        try {
            CallableStatement pstmt = cn.prepareCall(sql);
            pstmt.setString(1, t.getCareer_id());
            pstmt.setString(2, t.getCareer_name());
            pstmt.setString(3, t.getCareer_description());
            pstmt.setInt(4, t.getCareer_duration_years());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("No se insertó ninguna fila");
            }
        } catch (SQLException ex) {
            System.err.println("Ocurrio un error al agregar en la base de datos:" + ex.getMessage());
        }

    }

    @Override
    public void update(Career t) {
        Connection cn = ConnectionDB.getConnection();
        String sql = "call `sp_update_career`(?, ?, ?, ?);";
        try {
            CallableStatement pstmt = cn.prepareCall(sql);
            pstmt.setString(1, t.getCareer_id());
            pstmt.setString(2, t.getCareer_name());
            pstmt.setString(3, t.getCareer_description());
            pstmt.setInt(4, t.getCareer_duration_years());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("No se actualizo ninguna fila");
            }
        } catch (SQLException ex) {
            System.err.println("Ocurrio un error al actualizar en la base de datos:" + ex.getMessage());
        }
    }

    @Override
    public void deleteById(String i) {
        Connection cn = ConnectionDB.getConnection();
        String sql = "call `sp_delete_career_by_id`(?);";
        try {
            CallableStatement pstmt = cn.prepareCall(sql);
            pstmt.setString(1, i);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("No se actualizo ninguna fila");
            }
        } catch (SQLException ex) {
            System.err.println("Ocurrio un error al eliminar en la base de datos:" + ex.getMessage());
        }
    }

    @Override
    public Career findById(String id) {
        Connection cn = null;
        CallableStatement pstmt = null;
        ResultSet rs = null;
        Career career = null;

        try {
            cn = ConnectionDB.getConnection();
            String sql = "call `sp_find_by_id_career`(?)";
            pstmt = cn.prepareCall(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {  // Mover a la primera fila primero
                career = new Career();
                career.setCareer_id(rs.getString("career_id"));
                career.setCareer_name(rs.getString("career_name"));
                career.setCareer_description(rs.getString("career_description"));
                career.setCareer_duration_years(rs.getInt("career_duration_years"));
                career.setCharacteristicList(new LinkedList<>());

                // Procesar primera fila (si tiene características)
                if (rs.getString(5) != null) {
                    Characteristic characteristic = new Characteristic();
                    characteristic.setCharacteristics_id(rs.getString("characteristics_id"));
                    characteristic.setCharacteristics_name(rs.getString("characteristics_name"));
                    characteristic.setCharacteristics_description(rs.getString("characteristics_description"));
                    career.getCharacteristicList().add(characteristic);
                }

                // Procesar filas adicionales (si existen)
                while (rs.next()) {
                    Characteristic characteristic = new Characteristic();
                    characteristic.setCharacteristics_id(rs.getString("characteristics_id"));
                    characteristic.setCharacteristics_name(rs.getString("characteristics_name"));
                    characteristic.setCharacteristics_description(rs.getString("characteristics_description"));
                    career.getCharacteristicList().add(characteristic);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al obtener carrera: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {rs.close();}
            } catch (SQLException e) {}
            try {if (pstmt != null) {pstmt.close();}
            } catch (SQLException e) {}
            try {if (cn != null) {cn.close();}
            } catch (SQLException e) {}
        }

        return career;
    }

}
