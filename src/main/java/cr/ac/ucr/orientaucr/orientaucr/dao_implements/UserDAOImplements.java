package cr.ac.ucr.orientaucr.orientaucr.dao_implements;

import cr.ac.ucr.orientaucr.orientaucr.connection.ConnectionDB;
import cr.ac.ucr.orientaucr.orientaucr.dao.UserDAO;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import java.sql.*;
import java.util.LinkedList;

public class UserDAOImplements implements UserDAO {

    @Override
    public LinkedList<User> getAll(String search) {
        LinkedList<User> list = new LinkedList<>();

        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("{CALL sp_search_users(?)}");
            cs.setString(1, search);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                list.add(mapUser(rs));

            }
            rs.close();
            cs.close();
            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al buscar usuarios: " + e.getMessage());
        }

        return list;
    }

    @Override
    public LinkedList<User> getAll() {
        LinkedList<User> list = new LinkedList<>();

        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("{CALL sp_get_all_users()}");
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                list.add(mapUser(rs));
            }

            rs.close();
            cs.close();
            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al obtener todos los usuarios: " + e.getMessage());
        }

        return list;
    }

    @Override
    public void add(User user) {
        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("{CALL sp_create_user(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            cs.setString(1, user.getUser_name());
            cs.setString(2, user.getUser_lastname());
            cs.setString(3, user.getUser_email());
            cs.setInt(4, user.getUser_phone_number());
            cs.setDate(5, new java.sql.Date(user.getUser_birthdate().getTime()));
            cs.setString(6, user.getUser_password());
            cs.setDouble(7, user.getUser_admission_average());
            cs.setBoolean(8, user.isUser_allow_email_notification());
            cs.setBoolean(9, user.isUser_allow_whatsapp_notification());
            cs.setString(10, user.getUserProfilePicture());
            cs.setString(11, user.getUser_role());
            int rowsAffected = cs.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Usuario insertado correctamente");
            }

            cs.close();
            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al crear usuario: " + e.getMessage());
        }
    }

    @Override
    public void update(User user) {
        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("{CALL sp_update_user(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)}");

            cs.setString(1, user.getUser_id());
            cs.setString(2, user.getUser_name());
            cs.setString(3, user.getUser_lastname());
            cs.setString(4, user.getUser_email());
            cs.setInt(5, user.getUser_phone_number());
            cs.setDate(6, new java.sql.Date(user.getUser_birthdate().getTime()));
            cs.setString(7, user.getUser_password());
            cs.setDouble(8, user.getUser_admission_average());
            cs.setBoolean(9, user.isUser_allow_email_notification());
            cs.setBoolean(10, user.isUser_allow_whatsapp_notification());
            cs.setString(11, user.getUserProfilePicture());
            cs.setString(12, user.getUser_role());

            int rowsAffected = cs.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Usuario actualizado correctamente");
            }
            cs.close();
            cn.close();
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("{CALL sp_delete_user(?)}");

            cs.setString(1, id);
            int rowsAffected = cs.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Usuario eliminado correctamente");
            }
            cs.close();
            cn.close();
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        }
    }

    @Override
    public User findById(String id) {
        User user = null;
        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("{CALL sp_get_user_by_id(?)}");
            cs.setString(1, id);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                user = mapUser(rs);
            }
            rs.close();
            cs.close();
            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por ID: " + e.getMessage());
        }

        System.out.println("111  "+user.toString());
        return user;
    }

private User mapUser(ResultSet rs) throws SQLException {
    User user = new User();
    
    if (rs == null || rs.getMetaData() == null) {
        throw new SQLException("ResultSet inválido o vacío");
    }
    
    try {
        // Campos obligatorios
        user.setUser_id(rs.getString("user_id"));
        user.setUser_name(rs.getString("user_name"));
        user.setUser_lastname(rs.getString("user_lastname"));
        user.setUser_email(rs.getString("user_email"));
        
        // Campos opcionales con manejo de null
        int phoneNumber = rs.getInt("user_phone_number");
        user.setUser_phone_number(rs.wasNull() ? null : phoneNumber);
        
        Date birthdate = rs.getDate("user_birthdate");
        user.setUser_birthdate(birthdate);
        
        double admissionAvg = rs.getDouble("user_admission_average");
        user.setUser_admission_average(rs.wasNull() ? null : admissionAvg);
        
        user.setUser_allow_email_notification(rs.getBoolean("user_allow_email_notification"));
        user.setUser_allow_whatsapp_notification(rs.getBoolean("user_allow_whatsapp_notification"));
        
        Date createAt = rs.getDate("create_at");
        user.setCreate_at(createAt != null ? createAt.toLocalDate() : null);
        
        user.setUser_role(rs.getString("user_role"));
        user.setUserProfilePicture(rs.getString("user_profile_picture"));
    } catch (SQLException e) {
        System.err.println("Error al mapear columna: " + e.getMessage());
        throw e;
    }
    
    return user;
}
}
