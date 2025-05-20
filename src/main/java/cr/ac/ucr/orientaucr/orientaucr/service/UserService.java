package cr.ac.ucr.orientaucr.orientaucr.service;

import cr.ac.ucr.orientaucr.orientaucr.dao_implements.UserDAOImplements;
import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import cr.ac.ucr.orientaucr.orientaucr.domain.Role;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import java.util.LinkedList;

public class UserService {

    public UserService() {
    }

    private static final UserDAOImplements dataUser = new UserDAOImplements();

    public static LinkedList<User> getAllUsers() {
        return dataUser.getAll();
    }

    public static LinkedList<User> searchUsers(String searchTerm) {
        return dataUser.getAll(searchTerm);
    }

    public static User getUserById(String userId) {
        return dataUser.findById(userId);
    }

    public static void createUser(User user) {
        dataUser.add(user);
    }

    public static void updateUser(User user) {
        dataUser.update(user);
    }

    public static void deleteUser(String userId) {
        dataUser.deleteById(userId);
    }

    public static User findById(String userId) {
        return dataUser.findById(userId);
    }

    public  static User authenticate(String email, String password) {
        return dataUser.authenticateUser(email, password);
    }
    
    public static LinkedList<Permission> getAllPermissionOfUser(String user_id, String role_id){
        return dataUser.getAllPermissionOfUser(user_id, role_id);
    }
    
    public static LinkedList<Permission> getAllPermissionOfRole(String role_id){
        return dataUser.getAllPermissionOfRole(role_id);
    }
    
    public static LinkedList<Role> getAllRoles(){
        return dataUser.gerAllRoles();
    }
    
    public static void updateUserRoleAndPermission(String userId, String roleId, LinkedList<String> idPermissions) {
        dataUser.updateUserRoleAndPermission(userId, roleId, idPermissions);
    }
}
