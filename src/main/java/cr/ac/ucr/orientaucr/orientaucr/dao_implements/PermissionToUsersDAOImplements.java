package cr.ac.ucr.orientaucr.orientaucr.dao_implements;

import cr.ac.ucr.orientaucr.orientaucr.connection.ConnectionDB;
import cr.ac.ucr.orientaucr.orientaucr.dao.PermissionsDAO;
import cr.ac.ucr.orientaucr.orientaucr.domain.PermissionToUsers;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class PermissionToUsersDAOImplements implements PermissionsDAO {

    @Override
    public LinkedList<PermissionToUsers> getAllRolesOrPermissions(String rol_id) {
    LinkedList<PermissionToUsers> list = new LinkedList<>();

    try {
        Connection cn = ConnectionDB.getConnection();
        CallableStatement cs = cn.prepareCall("{CALL get_all_RolesOrPermission(?)}");

        if (rol_id != null) {
            cs.setString(1, rol_id);
        } else {
            cs.setNull(1, java.sql.Types.CHAR);
        }

        ResultSet rs = cs.executeQuery();

        while (rs.next()) {
            PermissionToUsers per = new PermissionToUsers();

            if (rol_id == null) {
                // Caso: rol_id es null → traer rol_id y rol_name
                per.setRol_id(rs.getString("rol_id"));
                per.setRol_name(rs.getString("rol_name"));
            } else {
                // Caso: rol_id tiene valor → traer rol_name, permission_name, descripcion
                per.setRol_name(rs.getString("rol_name"));
                per.setPermission_name(rs.getString("permission_name"));
                per.setDescripcion(rs.getString("descripcion"));
            }

            list.add(per);
        }

    } catch (SQLException e) {
        System.err.println("Error al ejecutar cargar: " + e.getMessage());
    }

    System.out.println("Se cargó la lista");
    return list;
}


    @Override
    public LinkedList<PermissionToUsers> getAllPermissionOfUser(String user_id) {
        LinkedList<PermissionToUsers> list = new LinkedList<>();

        try (Connection cn = ConnectionDB.getConnection(); CallableStatement cs = cn.prepareCall("{CALL sp_get_all_PermissionOfUsers(?)}")) {

            if (user_id != null) {
                cs.setString(1, user_id.trim());
            } else {
                cs.setNull(1, java.sql.Types.CHAR);
            }

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                PermissionToUsers per = new PermissionToUsers();
                per.setPermission_name(rs.getString("permission_name"));
                per.setDescripcion(rs.getString("descripcion"));
                list.add(per);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener permisos del usuario: " + e.getMessage());
        }

        return list;
    }

    @Override
    public void getUserRol(String user_id) {
        try (Connection cn = ConnectionDB.getConnection(); CallableStatement cs = cn.prepareCall("{CALL sp_get_user_rol_name(?)}")) {

            cs.setString(1, user_id.trim());

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                String userName = rs.getString("user_name");
                String roleName = rs.getString("rol_name");

                System.out.println("Usuario: " + userName + ", Rol: " + roleName);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener el rol del usuario: " + e.getMessage());
        }
    }

    @Override
    public void add(String user_id, String permission_id) {
        try (Connection cn = ConnectionDB.getConnection(); CallableStatement cs = cn.prepareCall("{CALL sp_insert_user_permission(?, ?)}")) {

            cs.setString(1, user_id.trim());
            cs.setString(2, permission_id.trim());

            cs.executeUpdate();

            System.out.println("Permiso agregado al usuario exitosamente.");

        } catch (SQLException e) {
            System.err.println("Error al agregar permiso al usuario: " + e.getMessage());
        }
    }

    @Override
    public void update(String user_id, String rol_id) {
        try (Connection cn = ConnectionDB.getConnection(); CallableStatement cs = cn.prepareCall("{CALL update_user_role(?, ?)}")) {

            cs.setString(1, user_id.trim());
            cs.setString(2, rol_id.trim());

            cs.executeUpdate();

            System.out.println("Rol del usuario actualizado correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al actualizar el rol del usuario: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(String user_id, String permission_id) {
        try (Connection cn = ConnectionDB.getConnection(); CallableStatement cs = cn.prepareCall("{CALL sp_delete_user_permission(?, ?)}")) {

            cs.setString(1, user_id.trim());
            cs.setString(2, permission_id.trim());

            cs.executeUpdate();

            System.out.println("Permiso eliminado del usuario correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al eliminar permiso del usuario: " + e.getMessage());
        }
    }
     @Override
public String FindById(String user_id) {
    String rolName = null;

    try (Connection cn = ConnectionDB.getConnection();
         CallableStatement cs = cn.prepareCall("{CALL sp_get_user_rol_name(?)}")) {

        cs.setString(1, user_id);  // Se pasa correctamente el ID del usuario

        try (ResultSet rs = cs.executeQuery()) {
            if (rs.next()) {
                rolName = rs.getString("rol_name");  // Se obtiene solo el rol_name
            }
        }

        System.out.println("Consulta de rol realizada .");

    } catch (SQLException e) {
        System.err.println("Error al obtener el rol por ID: " + e.getMessage());
    }
 System.out.print(rolName);
    return rolName;
}
}
