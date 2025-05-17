
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


public class CareerDAOImplements implements CareerDAO{

    @Override
    public LinkedList<Career> getAll(String search) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public LinkedList<Career> getAll() {
        LinkedList<Career> listCareer = new LinkedList<>();
        Map<String, Career> map = new HashMap<>();
        Connection cn = ConnectionDB.getConnection();
        String sql = "call guides_ucr.sp_getAllCareers();";

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
                
                if(characteristics_id != null){
                    Characteristic characteristic = new Characteristic();
                    characteristic.setCharacteristics_id(characteristics_id);
                    characteristic.setCharacteristics_name(characteristics_name);
                    characteristic.setCharacteristics_description(characteristics_description);
                
                    career.getCharacteristicList().add(characteristic);
                }                
            }
        } catch (SQLException ex) {
            System.err.println("Ocurrio un error en la consulta de base de datos:"+ ex.getMessage());
        }
        return listCareer;
    }

    @Override
    public void add(Career t) {
        Connection cn = ConnectionDB.getConnection();
        String sql = "call guides_ucr.sp_addCareer(?, ?, ?, ?);";
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
            System.err.println("Ocurrio un error al agregar en la base de datos:"+ ex.getMessage());
        }
        
        
    }

    @Override
    public void update(Career t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override

    public void deleteById(String i) {

        }

    @Override
    public Career findById(String i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    

    
    
}
