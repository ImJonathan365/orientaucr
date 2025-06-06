package cr.ac.ucr.orientaucr.orientaucr.services;

import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import java.util.List;

public interface lRolesService {
    List<Roles> getAllRoles();
    Roles getRoleById(String id);
    void  createRole(Roles role);
    void  updateRoleWithPermissions(Roles role);
    void deleteRole(String id);
    List<Permission> getAllPermissions();
    Roles getRoleWithPermissions(String id);
     List<Roles> getAllRolesWithPermissions();
    void assignPermissionToRole(String roleId, String permissionId);
    void deletePermissionsFromRole(String roleId);
}