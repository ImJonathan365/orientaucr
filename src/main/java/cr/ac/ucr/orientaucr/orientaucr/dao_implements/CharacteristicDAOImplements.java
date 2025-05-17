package cr.ac.ucr.orientaucr.orientaucr.dao_implements;

import cr.ac.ucr.orientaucr.orientaucr.connection.ConnectionDB;
import cr.ac.ucr.orientaucr.orientaucr.dao.CharacteristicDAO;
import cr.ac.ucr.orientaucr.orientaucr.domain.Characteristic;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class CharacteristicDAOImplements implements CharacteristicDAO {

    @Override
    public LinkedList<Characteristic> getAll(String search) {
        return null;
    }

    @Override
    public LinkedList<Characteristic> getAll() {
        LinkedList<Characteristic> list = new LinkedList<>();

        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("CALL get_all_characteristics()");
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Characteristic c = new Characteristic();
                c.setCharacteristics_id(rs.getString(1));
                c.setCharacteristics_name(rs.getString(2));
                c.setCharacteristics_description(rs.getString(3));
                list.add(c);
            }

            rs.close();
            cs.close();
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error Chracteristic getAll: " + e.getMessage());
        }

        return list;
    }

    @Override
    public void add(Characteristic t) {

        try {

            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("CALL add_characteristic(?, ?, ?)");
            cs.setString(1, t.getCharacteristics_id());
            cs.setString(2, t.getCharacteristics_name());
            cs.setString(3, t.getCharacteristics_description());
            cs.executeUpdate();

            cs.close();
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error add: " + e.getMessage());
        }

    }

    @Override
    public void update(Characteristic t) {

        try {

            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("CALL update_characteristic(?, ?, ?)");
            cs.setString(1, t.getCharacteristics_id());
            cs.setString(2, t.getCharacteristics_name());
            cs.setString(3, t.getCharacteristics_description());
            cs.executeUpdate();

            cs.close();
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error update: " + e.getMessage());
        }

    }

    @Override
    public void deleteById(String id) {

        try {

            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("CALL delete_characteristic(?)");
            cs.setString(1, id);
            cs.executeUpdate();

            cs.close();
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error deleteById: " + e.getMessage());
        }

    }

    @Override
    public Characteristic findById(String id) {
        Characteristic c = null;

        try {
            
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("CALL get_characteristic_by_id(?)");
            cs.setString(1, id);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                c = new Characteristic();
                c.setCharacteristics_id(rs.getString(1));
                c.setCharacteristics_name(rs.getString(2));
                c.setCharacteristics_description(rs.getString(3));
            }

            rs.close();
            cs.close();
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error findById: " + e.getMessage());
        }

        return c;
    }

}
