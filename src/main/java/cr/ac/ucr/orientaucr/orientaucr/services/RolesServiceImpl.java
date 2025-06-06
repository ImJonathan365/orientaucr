package cr.ac.ucr.orientaucr.orientaucr.services;

import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import cr.ac.ucr.orientaucr.orientaucr.repository.CustomRolesRepository;
import cr.ac.ucr.orientaucr.orientaucr.repository.RolesRepository;
import cr.ac.ucr.orientaucr.orientaucr.repository.PermissionRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.lRolesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RolesServiceImpl implements lRolesService {

    private final RolesRepository rolesRepository;
    private final PermissionRepository permissionRepository;
    private final CustomRolesRepository customRepository;

    @Autowired
    public RolesServiceImpl(RolesRepository rolesRepository, PermissionRepository permissionRepository, cr.ac.ucr.orientaucr.orientaucr.repository.CustomRolesRepository customRepository) {
        this.rolesRepository = rolesRepository;
        this.permissionRepository = permissionRepository;
        this.customRepository = customRepository;
    }

    @Override
    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }

    @Override
    public Roles getRoleById(String id) {
        return rolesRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void createRole(Roles role) {
        try {

            if (role.getRolId() == null) {
                role.setRolId(UUID.randomUUID().toString());
            }

            // 1. Guardar el rol usando el stored procedure
            customRepository.addRole(role.getRolId(), role.getRolName());

            // 2. Asignar permisos si existen
            if (role.getPermissions() != null && !role.getPermissions().isEmpty()) {
                for (Permission permission : role.getPermissions()) {
                    customRepository.assignPermissionToRole(role.getRolId(), permission.getPermissionId());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al crear el rol: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void updateRoleWithPermissions(Roles role) {
        try {
            // 1. Actualizar los datos básicos del rol
            customRepository.updateRole(role.getRolId(), role.getRolName());

            // 2. Eliminar todos los permisos actuales
            customRepository.deletePermissionsFromRole(role.getRolId());

            // 3. Asignar los nuevos permisos
            if (role.getPermissions() != null && !role.getPermissions().isEmpty()) {
                for (Permission permission : role.getPermissions()) {
                    customRepository.assignPermissionToRole(
                            role.getRolId(),
                            permission.getPermissionId()
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el rol: " + e.getMessage(), e);
        }

    }

    @Override
    @Transactional
    public void deleteRole(String id) {
        rolesRepository.deleteById(id);
    }

    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public Roles getRoleWithPermissions(String id) {
        return rolesRepository.findById(id).orElse(null);
    }

    @Override
    public List<Roles> getAllRolesWithPermissions() {
        return customRepository.getAllRolesWithPermissions();
    }

    @Override
    @Transactional
    public void assignPermissionToRole(String roleId, String permissionId) {
        customRepository.assignPermissionToRole(roleId, permissionId);
    }

    @Override
    @Transactional
    public void deletePermissionsFromRole(String roleId) {
        customRepository.deletePermissionsFromRole(roleId);
    }

}
