
package cr.ac.ucr.orientaucr.orientaucr.dao;

import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import cr.ac.ucr.orientaucr.orientaucr.domain.Role;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import java.util.LinkedList;

public interface UserDAO extends CRUD<User>{
    
    public User authenticateUser(String email, String password);
    public LinkedList<Permission> getAllPermissionOfUser(String user_id, String role_id);
    public LinkedList<Permission> getAllPermissionOfRole(String role_id);
    public LinkedList<Role> gerAllRoles();
    public void updateUserRoleAndPermission(String userId, String roleId, LinkedList<String> idPermissions);
    
}
