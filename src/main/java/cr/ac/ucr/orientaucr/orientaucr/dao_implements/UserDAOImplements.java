package cr.ac.ucr.orientaucr.orientaucr.dao_implements;

import cr.ac.ucr.orientaucr.orientaucr.connection.ConnectionDB;
import cr.ac.ucr.orientaucr.orientaucr.dao.UserDAO;
import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.UUID;

public class UserDAOImplements implements UserDAO {

    public UserDAOImplements() {}

    @Override
    public User authenticateUser(String email, String password) {
        User user = null;

        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("CALL sp_authenticate_user(?, ?)");
            cs.setString(1, email);
            cs.setString(2, password);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUser_id(rs.getString("user_id"));
                user.setUser_name(rs.getString("user_name"));
                user.setUser_lastname(rs.getString("user_lastname"));
                user.setUser_email(rs.getString("user_email"));
                user.setUser_phone_number(rs.getInt("user_phone_number"));
                user.setUser_birthdate(rs.getDate("user_birthdate"));
                user.setUser_password(rs.getString("user_password"));
                user.setUser_admission_average(rs.getDouble("user_admission_average"));
                user.setUser_profile_picture(rs.getString("user_profile_picture"));
                user.setUser_allow_email_notification(rs.getBoolean("user_allow_email_notification"));

                LinkedList<Roles> roles = new LinkedList<>();
                CallableStatement csRoles = cn.prepareCall("CALL sp_get_roles_by_user_id(?)");
                csRoles.setString(1, user.getUser_id());
                ResultSet rsRoles = csRoles.executeQuery();

                while (rsRoles.next()) {
                    Roles role = new Roles(
                            rsRoles.getString("rol_id"),
                            rsRoles.getString("rol_name"),
                            new LinkedList<>()
                    );

                    LinkedList<Permission> permissions = new LinkedList<>();
                    CallableStatement csPerms = cn.prepareCall("CALL sp_get_permissions_by_role_id(?)");
                    csPerms.setString(1, role.getRol_id());
                    ResultSet rsPerms = csPerms.executeQuery();

                    while (rsPerms.next()) {
                        permissions.add(new Permission(
                                rsPerms.getString("permission_id"),
                                rsPerms.getString("permission_name"),
                                rsPerms.getString("permission_description")
                        ));
                    }

                    csPerms.close();
                    role.setPermissions(permissions);
                    roles.add(role);
                }

                csRoles.close();
                user.setUser_roles(roles);
            }

            rs.close();
            cs.close();
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error authenticate User: " + e.getMessage());
        }

        return user;
    }

    @Override
    public LinkedList<User> getAll(String search) {
        LinkedList<User> list = new LinkedList<>();

        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("CALL sp_search_users()");
            cs.setString(1, search);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getString("user_id"));
                user.setUser_name(rs.getString("user_name"));
                user.setUser_lastname(rs.getString("user_lastname"));
                user.setUser_email(rs.getString("user_email"));
                user.setUser_phone_number(rs.getInt("user_phone_number"));
                user.setUser_birthdate(rs.getDate("user_birthdate"));
                user.setUser_password(rs.getString("user_password"));
                user.setUser_admission_average(rs.getDouble("user_admission_average"));
                user.setUser_profile_picture(rs.getString("user_profile_picture"));
                user.setUser_allow_email_notification(rs.getBoolean("user_allow_email_notification"));

                LinkedList<Roles> roles = new LinkedList<>();
                CallableStatement csRoles = cn.prepareCall("CALL sp_get_roles_by_user_id(?)");
                csRoles.setString(1, user.getUser_id());
                ResultSet rsRoles = csRoles.executeQuery();

                while (rsRoles.next()) {
                    Roles role = new Roles(
                            rsRoles.getString("rol_id"),
                            rsRoles.getString("rol_name"),
                            new LinkedList<>()
                    );

                    LinkedList<Permission> permissions = new LinkedList<>();
                    CallableStatement csPerms = cn.prepareCall("CALL sp_get_permissions_by_role_id(?)");
                    csPerms.setString(1, role.getRol_id());
                    ResultSet rsPerms = csPerms.executeQuery();

                    while (rsPerms.next()) {
                        permissions.add(new Permission(
                                rsPerms.getString("permission_id"),
                                rsPerms.getString("permission_name"),
                                rsPerms.getString("permission_description")
                        ));
                    }

                    csPerms.close();
                    role.setPermissions(permissions);
                    roles.add(role);
                }

                csRoles.close();
                user.setUser_roles(roles);
                list.add(user);
            }

            rs.close();
            cs.close();
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error getAll Users: " + e.getMessage());
        }

        return list;
    }

    @Override
    public LinkedList<User> getAll() {
        LinkedList<User> list = new LinkedList<>();

        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement cs = cn.prepareCall("CALL sp_get_all_users()");
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getString("user_id"));
                user.setUser_name(rs.getString("user_name"));
                user.setUser_lastname(rs.getString("user_lastname"));
                user.setUser_email(rs.getString("user_email"));
                user.setUser_phone_number(rs.getInt("user_phone_number"));
                user.setUser_birthdate(rs.getDate("user_birthdate"));
                user.setUser_password(rs.getString("user_password"));
                user.setUser_admission_average(rs.getDouble("user_admission_average"));
                user.setUser_profile_picture(rs.getString("user_profile_picture"));
                user.setUser_allow_email_notification(rs.getBoolean("user_allow_email_notification"));

                LinkedList<Roles> roles = new LinkedList<>();
                CallableStatement csRoles = cn.prepareCall("CALL sp_get_roles_by_user_id(?)");
                csRoles.setString(1, user.getUser_id());
                ResultSet rsRoles = csRoles.executeQuery();

                while (rsRoles.next()) {
                    Roles role = new Roles(
                            rsRoles.getString("rol_id"),
                            rsRoles.getString("rol_name"),
                            new LinkedList<>()
                    );

                    LinkedList<Permission> permissions = new LinkedList<>();
                    CallableStatement csPerms = cn.prepareCall("CALL sp_get_permissions_by_role_id(?)");
                    csPerms.setString(1, role.getRol_id());
                    ResultSet rsPerms = csPerms.executeQuery();

                    while (rsPerms.next()) {
                        permissions.add(new Permission(
                                rsPerms.getString("permission_id"),
                                rsPerms.getString("permission_name"),
                                rsPerms.getString("permission_description")
                        ));
                    }

                    csPerms.close();
                    role.setPermissions(permissions);
                    roles.add(role);
                }

                csRoles.close();
                user.setUser_roles(roles);
                list.add(user);
            }

            rs.close();
            cs.close();
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error getAll Users: " + e.getMessage());
        }

        return list;
    }

    @Override
    public void add(User user) {
        try {
            user.setUser_id(UUID.randomUUID().toString());

            Connection cn = ConnectionDB.getConnection();

            CallableStatement cs = cn.prepareCall("CALL sp_add_user(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            cs.setString(1, user.getUser_id());
            cs.setString(2, user.getUser_name());
            cs.setString(3, user.getUser_lastname());
            cs.setString(4, user.getUser_email());
            cs.setInt(5, user.getUser_phone_number());
            cs.setDate(6, new java.sql.Date(user.getUser_birthdate().getTime()));
            cs.setString(7, user.getUser_password());
            cs.setDouble(8, user.getUser_admission_average());
            cs.setString(9, user.getUser_profile_picture());
            cs.setBoolean(10, user.isUser_allow_email_notification());
            cs.executeUpdate();
            cs.close();

            for (Roles role : user.getUser_roles()) {
                CallableStatement csRole = cn.prepareCall("CALL sp_add_role_to_user(?, ?)");
                csRole.setString(1, user.getUser_id());
                csRole.setString(2, role.getRol_id());
                csRole.executeUpdate();
                csRole.close();

                for (Permission permission : role.getPermissions()) {
                    CallableStatement csPerm = cn.prepareCall("CALL sp_add_permission_to_role(?, ?)");
                    csPerm.setString(1, role.getRol_id());
                    csPerm.setString(2, permission.getPermission_id());
                    csPerm.executeUpdate();
                    csPerm.close();
                }
            }

            cn.close();

        } catch (SQLException e) {
            System.out.println("Error add User: " + e.getMessage());
        }
    }

    @Override
    public void update(User user) {
        try {
            Connection cn = ConnectionDB.getConnection();

            CallableStatement cs = cn.prepareCall("CALL sp_update_user(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            cs.setString(1, user.getUser_id());
            cs.setString(2, user.getUser_name());
            cs.setString(3, user.getUser_lastname());
            cs.setString(4, user.getUser_email());
            cs.setInt(5, user.getUser_phone_number());
            cs.setDate(6, new java.sql.Date(user.getUser_birthdate().getTime()));
            cs.setString(7, user.getUser_password());
            cs.setDouble(8, user.getUser_admission_average());
            cs.setString(9, user.getUser_profile_picture());
            cs.setBoolean(10, user.isUser_allow_email_notification());
            cs.executeUpdate();
            cs.close();

            CallableStatement csDeleteRoles = cn.prepareCall("CALL sp_delete_roles_from_user(?)");
            csDeleteRoles.setString(1, user.getUser_id());
            csDeleteRoles.executeUpdate();
            csDeleteRoles.close();

            for (Roles role : user.getUser_roles()) {
                CallableStatement csRole = cn.prepareCall("CALL sp_add_role_to_user(?, ?)");
                csRole.setString(1, user.getUser_id());
                csRole.setString(2, role.getRol_id());
                csRole.executeUpdate();
                csRole.close();

                for (Permission permission : role.getPermissions()) {
                    CallableStatement csPerm = cn.prepareCall("CALL sp_add_permission_to_role(?, ?)");
                    csPerm.setString(1, role.getRol_id());
                    csPerm.setString(2, permission.getPermission_id());
                    csPerm.executeUpdate();
                    csPerm.close();
                }
            }

            cn.close();

        } catch (SQLException e) {
            System.out.println("Error update User: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            Connection cn = ConnectionDB.getConnection();

            CallableStatement csDeleteRoles = cn.prepareCall("CALL sp_delete_roles_from_user(?)");
            csDeleteRoles.setString(1, id);
            csDeleteRoles.executeUpdate();
            csDeleteRoles.close();

            CallableStatement csDeleteUser = cn.prepareCall("CALL sp_delete_user(?)");
            csDeleteUser.setString(1, id);
            csDeleteUser.executeUpdate();
            csDeleteUser.close();

            cn.close();

        } catch (SQLException e) {
            System.out.println("Error delete User: " + e.getMessage());
        }
    }

    @Override
    public User findById(String id) {
        User user = null;

        try {
            Connection cn = ConnectionDB.getConnection();

            CallableStatement cs = cn.prepareCall("CALL sp_get_user_by_id(?)");
            cs.setString(1, id);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUser_id(rs.getString("user_id"));
                user.setUser_name(rs.getString("user_name"));
                user.setUser_lastname(rs.getString("user_lastname"));
                user.setUser_email(rs.getString("user_email"));
                user.setUser_phone_number(rs.getInt("user_phone_number"));
                user.setUser_birthdate(rs.getDate("user_birthdate"));
                user.setUser_password(rs.getString("user_password"));
                user.setUser_admission_average(rs.getDouble("user_admission_average"));
                user.setUser_profile_picture(rs.getString("user_profile_picture"));
                user.setUser_allow_email_notification(rs.getBoolean("user_allow_email_notification"));

                LinkedList<Roles> rolesList = new LinkedList<>();

                CallableStatement csRoles = cn.prepareCall("CALL sp_get_roles_by_user_id(?)");
                csRoles.setString(1, id);
                ResultSet rsRoles = csRoles.executeQuery();

                while (rsRoles.next()) {
                    String rolId = rsRoles.getString("rol_id");
                    String rolName = rsRoles.getString("rol_name");

                    LinkedList<Permission> permissionsList = new LinkedList<>();

                    CallableStatement csPerms = cn.prepareCall("CALL sp_get_permissions_by_role_id(?)");
                    csPerms.setString(1, rolId);
                    ResultSet rsPerms = csPerms.executeQuery();

                    while (rsPerms.next()) {
                        Permission perm = new Permission(
                                rsPerms.getString("permission_id"),
                                rsPerms.getString("permission_name"),
                                rsPerms.getString("permission_description")
                        );
                        permissionsList.add(perm);
                    }

                    csPerms.close();
                    rsPerms.close();

                    Roles role = new Roles(rolId, rolName, permissionsList);
                    rolesList.add(role);
                }

                csRoles.close();
                rsRoles.close();

                user.setUser_roles(rolesList);
            }

            cs.close();
            rs.close();
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error findById User: " + e.getMessage());
        }

        return user;
    }

}
