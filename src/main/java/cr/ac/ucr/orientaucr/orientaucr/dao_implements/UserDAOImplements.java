package cr.ac.ucr.orientaucr.orientaucr.dao_implements;

import cr.ac.ucr.orientaucr.orientaucr.connection.ConnectionDB;
import cr.ac.ucr.orientaucr.orientaucr.dao.UserDAO;
import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import cr.ac.ucr.orientaucr.orientaucr.domain.Role;
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
            System.out.println(user.getUser_role());
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

        System.out.println("111  " + user.toString());
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
            user.setUser_password(rs.getString("user_password"));

        } catch (SQLException e) {
            System.err.println("Error al mapear columna: " + e.getMessage());
            throw e;
        }

        return user;
    }

    @Override
    public User authenticateUser(String email, String password) {
        User user = null;
        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("{CALL sp_authenticate_user(?, ?)}");
            cs.setString(1, email);
            cs.setString(2, password);

            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                user = mapUser(rs);
            }

            rs.close();
            cs.close();
            cn.close();
        } catch (SQLException e) {
            System.err.println("Error en autenticación: " + e.getMessage());
            throw new RuntimeException("Error de autenticación: " + e.getMessage());
        }
        return user;
    }

    @Override
    public LinkedList<Permission> getAllPermissionOfUser(String user_id, String role_id) {
        LinkedList<Permission> permissions = new LinkedList<>();

        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("{CALL sp_get_all_permissions_of_user_and_role(?, ?)}");
            cs.setString(1, user_id);
            cs.setString(2, role_id);

            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Permission p = new Permission();
                p.setPermission_id(rs.getString(1));
                p.setPermission_name(rs.getString(2));
                p.setPermission_description(rs.getString(3));
                permissions.add(p);
            }

            rs.close();
            cs.close();
            cn.close();
        } catch (SQLException e) {
            System.err.println("Error al obtener permisos del usuario: " + e.getMessage());
        }

        return permissions;
    }

    @Override
    public LinkedList<Permission> getAllPermissionOfRole(String role_id) {
        LinkedList<Permission> permissions = new LinkedList<>();

        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("{CALL sp_get_all_permissions_of_role(?)}");
            cs.setString(1, role_id);

            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Permission p = new Permission();
                p.setPermission_id(rs.getString(1));
                p.setPermission_name(rs.getString(2));
                p.setPermission_description(rs.getString(3));
                permissions.add(p);
            }

            rs.close();
            cs.close();
            cn.close();
        } catch (SQLException e) {
            System.err.println("Error al obtener permisos del rol: " + e.getMessage());
        }

        return permissions;
    }

    @Override
    public LinkedList<Role> gerAllRoles() {
        LinkedList<Role> roles = new LinkedList<>();

        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("{CALL sp_get_all_roles()}");

            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Role r = new Role();
                r.setRol_id(rs.getString(1));
                r.setRol_name(rs.getString(2));
                roles.add(r);
            }

            rs.close();
            cs.close();
            cn.close();
        } catch (SQLException e) {
            System.err.println("Error al obtener roles: " + e.getMessage());
        }

        return roles;
    }

    @Override
    public void updateUserRoleAndPermission(String userId, String roleId, LinkedList<String> idPermissions) {
        try {
            
            System.out.println(userId + "-" + roleId + "-");
            for (String x : idPermissions){
            System.out.println(x);
            }
            
            
            Connection cn = ConnectionDB.getConnection();
            cn.setAutoCommit(false); // Para asegurar transacción

            // Actualizar el rol del usuario
            CallableStatement csRole = cn.prepareCall("CALL sp_update_user_role(?, ?);");
            csRole.setString(1, userId);
            csRole.setString(2, roleId);
            csRole.executeUpdate();
            csRole.close();

            // Eliminar todos los permisos anteriores
            PreparedStatement psDelete = cn.prepareStatement("DELETE FROM user_permission WHERE user_id = ?");
            psDelete.setString(1, userId);
            psDelete.executeUpdate();
            psDelete.close();

            // Insertar los nuevos permisos
            for (String permissionId : idPermissions) {
                CallableStatement csPerm = cn.prepareCall("CALL sp_insert_user_permission(?, ?);");
                csPerm.setString(1, userId);
                csPerm.setString(2, permissionId);
                csPerm.executeUpdate();
                csPerm.close();
            }

            cn.commit();
            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al actualizar rol y permisos: " + e.getMessage());
            throw new RuntimeException("No se pudo actualizar el usuario y sus permisos.");
        }
    }

}
