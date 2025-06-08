package cr.ac.ucr.orientaucr.orientaucr.repository;

import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import java.util.List;

public interface CustomRolesRepository {
    List<Roles> getAll();
    void assignPermissionToRole(String roleId, String permissionId);
    void deletePermissionsFromRole(String roleId);
    void addRole(String roleId, String roleName);
     void updateRole(String roleId, String roleName);
}