package cr.ac.ucr.orientaucr.orientaucr.repository;

import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class CustomRolesRepositoryImpl implements CustomRolesRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Roles> getAllRolesWithPermissions() {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("sp_get_roles_permissions")
                .registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);

        query.execute();

        List<Object[]> results = query.getResultList();
        // Map results to Roles objects
        // This would be similar to your previous DAO implementation
        return mapResultsToRoles(results);
    }

    @Override
    public void updateRole(String roleId, String roleName) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("sp_update_role")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN);

        query.setParameter(1, roleId);
        query.setParameter(2, roleName);
        query.execute();
    }

    @Override
    public Roles getRoleWithPermissionsById(String id) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("sp_get_role_by_id")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, void.class, ParameterMode.REF_CURSOR);

        query.setParameter(1, id);
        query.execute();

        List<Object[]> results = query.getResultList();
        // Map results to a single Roles object
        return mapResultsToSingleRole(results);
    }

    @Override
    public void assignPermissionToRole(String roleId, String permissionId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("sp_assign_permission_to_role")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN);

        query.setParameter(1, roleId);
        query.setParameter(2, permissionId);
        query.execute();
    }

    @Override
    public void deletePermissionsFromRole(String roleId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("sp_delete_permissions_from_role")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN);

        query.setParameter(1, roleId);
        query.execute();
    }

    private List<Roles> mapResultsToRoles(List<Object[]> results) {
        Map<String, Roles> rolesMap = new HashMap<>();

        for (Object[] row : results) {
            String roleId = (String) row[0];
            String roleName = (String) row[1];
            String permissionId = (String) row[2];
            String permissionName = (String) row[3];
            String permissionDescription = (String) row[4];

            Roles role = rolesMap.get(roleId);
            if (role == null) {
                role = new Roles();
                role.setRolId(roleId);
                role.setRolName(roleName);
                role.setPermissions(new LinkedList<>());
                rolesMap.put(roleId, role);
            }

            Permission permission = new Permission();
            permission.setPermissionId(permissionId);
            permission.setPermissionName(permissionName);
            permission.setPermissionDescription(permissionDescription);

            role.getPermissions().add(permission);
        }

        return new LinkedList<>(rolesMap.values());
    }

    private Roles mapResultsToSingleRole(List<Object[]> results) {
        Roles role = null;

        for (Object[] row : results) {
            String roleId = (String) row[0];
            String roleName = (String) row[1];
            String permissionId = (String) row[2];
            String permissionName = (String) row[3];
            String permissionDescription = (String) row[4];

            if (role == null) {
                role = new Roles();
                role.setRolId(roleId);
                role.setRolName(roleName);
                role.setPermissions(new LinkedList<>());
            }

            Permission permission = new Permission();
            permission.setPermissionId(permissionId);
            permission.setPermissionName(permissionName);
            permission.setPermissionDescription(permissionDescription);

            role.getPermissions().add(permission);
        }

        return role;
    }

    @Override
    public void addRole(String roleId, String roleName) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("sp_add_role")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN);

        query.setParameter(1, roleId);
        query.setParameter(2, roleName);
        query.execute();
    }
}
