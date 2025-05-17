package cr.ac.ucr.orientaucr.orientaucr.dao_implements;

import cr.ac.ucr.orientaucr.orientaucr.connection.ConnectionDB;
import cr.ac.ucr.orientaucr.orientaucr.dao.TestDAO;
import cr.ac.ucr.orientaucr.orientaucr.domain.Characteristic;
import cr.ac.ucr.orientaucr.orientaucr.domain.Test;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class TestDAOImplements implements TestDAO {

    public TestDAOImplements() {}
    
    @Override
    public LinkedList<Test> getAll(String search) {
        return null;
    }

    @Override
    public LinkedList<Test> getAll() {
        LinkedList<Test> list = new LinkedList<>();
        Map<String, Test> map = new HashMap<>();

        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("CALL get_vocational_test_questions();");
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                String questionId = rs.getString(1);
                String questionText = rs.getString(2);
                String characteristicId = rs.getString(3);
                String characteristicName = rs.getString(4);
                String characteristicDescription = rs.getString(5);

                Test test = map.get(questionId);
                if (test == null) {
                    test = new Test();
                    test.setQuestion_id(questionId);
                    test.setQuestion_text(questionText);
                    test.setCharacteristics(new LinkedList<>());
                    map.put(questionId, test);
                }

                Characteristic c = new Characteristic();
                c.setCharacteristics_id(characteristicId);
                c.setCharacteristics_name(characteristicName);
                c.setCharacteristics_description(characteristicDescription);

                test.getCharacteristics().add(c);

            }

            rs.close();
            cs.close();
            cn.close();

            list.addAll(map.values());

        } catch (SQLException e) {
            System.out.println("Error Test getAll: " + e.getMessage());
        }

        return list;
    }

    @Override
    public void add(Test t) {

        try {

            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("CALL add_vocational_question(?, ?)");
            cs.setString(1, t.getQuestion_id());
            cs.setString(2, t.getQuestion_text());
            cs.executeUpdate();

            for (Characteristic c : t.getCharacteristics()) {
                CallableStatement csChar = cn.prepareCall("CALL add_characteristic_to_question(?, ?)");
                csChar.setString(1, t.getQuestion_id());
                csChar.setString(2, c.getCharacteristics_id());
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
    public void update(Test t) {
        
        try {
            
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("CALL update_vocational_question(?, ?)");
            cs.setString(1, t.getQuestion_id());
            cs.setString(2, t.getQuestion_text());
            cs.executeUpdate();
            cs.close();

            CallableStatement deleteChar = cn.prepareCall("CALL delete_characteristics_from_question(?)");
            deleteChar.setString(1, t.getQuestion_id());
            deleteChar.executeUpdate();
            deleteChar.close();

            for (Characteristic c : t.getCharacteristics()) {
                CallableStatement csChar = cn.prepareCall("CALL add_characteristic_to_question(?, ?)");
                csChar.setString(1, t.getQuestion_id());
                csChar.setString(2, c.getCharacteristics_id());
                csChar.executeUpdate();
                csChar.close();
            }

            cn.close();

        } catch (SQLException e) {
            System.out.println("Error Test update: " + e.getMessage());
        }
        
    }

    @Override
    public void deleteById(String id) {

        try {

            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("CALL delete_vocational_question(?)");
            cs.setString(1, id);
            cs.executeUpdate();
            cs.close();
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error Test deleteById: " + e.getMessage());
        }

    }

    @Override
    public Test findById(String id) {

        Test test = null;

        try {

            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("CALL get_vocational_question_by_id(?)");
            cs.setString(1, id);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                if (test == null) {
                    test = new Test();
                    test.setQuestion_id(rs.getString(1));
                    test.setQuestion_text(rs.getString(2));
                    test.setCharacteristics(new LinkedList<>());
                }

                Characteristic c = new Characteristic();
                c.setCharacteristics_id(rs.getString(3));
                c.setCharacteristics_name(rs.getString(4));
                c.setCharacteristics_description(rs.getString(5));

                test.getCharacteristics().add(c);
            }

            rs.close();
            cs.close();
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error Test findById: " + e.getMessage());
        }

        return test;
    }

}
