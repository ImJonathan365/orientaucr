package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import cr.ac.ucr.orientaucr.orientaucr.services.lRolesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import cr.ac.ucr.orientaucr.orientaucr.repository.IPermissionRepository;
import cr.ac.ucr.orientaucr.orientaucr.repository.IRolesRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.lRolesService;
import java.util.Set;
import java.util.stream.Collectors;
import cr.ac.ucr.orientaucr.orientaucr.repository.lCustomRolesRepository;

@Service
public class RolesServiceJPA implements lRolesService {

    private final IRolesRepository rolesRepository;
    private final IPermissionRepository permissionRepository;
    private final lCustomRolesRepository customRepository;

    @Autowired
    public RolesServiceJPA(IRolesRepository rolesRepository, IPermissionRepository permissionRepository, cr.ac.ucr.orientaucr.orientaucr.repository.lCustomRolesRepository customRepository) {
        this.rolesRepository = rolesRepository;
        this.permissionRepository = permissionRepository;
        this.customRepository = customRepository;
    }

    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public List<Roles> getAll() {
        return customRepository.getAll();

    }

    @Override
    @Transactional
    public void add(Roles t) {
        String rolId = UUID.randomUUID().toString();
        try {
            List<Roles> existingRoles = rolesRepository.findAll();
            for (Roles existingRole : existingRoles) {
                if (existingRole.getRolName().equalsIgnoreCase(t.getRolName())) {
                    throw new RuntimeException("Ya existe un rol con el mismo nombre.");
                }

                if (samePermissions(existingRole.getPermissions(), t.getPermissions())) {
                    throw new RuntimeException("Ya existe un rol con los mismos permisos.");
                }
            }
            if (t.getRolId() == null) {
                t.setRolId(rolId);
            }
            customRepository.addRole(rolId, t.getRolName());

            if (t.getPermissions() != null && !t.getPermissions().isEmpty()) {
                for (Permission permission : t.getPermissions()) {
                    customRepository.assignPermissionToRole(rolId, permission.getPermissionId());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al crear el rol: " + e.getMessage(), e);
        }
    }

    private boolean samePermissions(List<Permission> list1, List<Permission> list2) {
        if (list1 == null || list2 == null) {
            return false;
        }
        if (list1.size() != list2.size()) {
            return false;
        }
        Set<String> set1 = list1.stream().map(Permission::getPermissionId).collect(Collectors.toSet());
        Set<String> set2 = list2.stream().map(Permission::getPermissionId).collect(Collectors.toSet());
        return set1.equals(set2);
    }

    @Override
    @Transactional
    public void update(Roles t) {
        try {
            List<Roles> existingRoles = rolesRepository.findAll();
            for (Roles existingRole : existingRoles) {
                if (existingRole.getRolId().equals(t.getRolId())) {
                    continue;
                }

                if (existingRole.getRolName().equalsIgnoreCase(t.getRolName())) {
                    throw new RuntimeException("Ya existe un rol con el mismo nombre.");
                }

                if (samePermissions(existingRole.getPermissions(), t.getPermissions())) {
                    throw new RuntimeException("Ya existe un rol con los mismos permisos.");
                }
            }
            customRepository.updateRole(t.getRolId(), t.getRolName());
            customRepository.deletePermissionsFromRole(t.getRolId());

            if (t.getPermissions() != null && !t.getPermissions().isEmpty()) {
                for (Permission permission : t.getPermissions()) {
                    customRepository.assignPermissionToRole(t.getRolId(), permission.getPermissionId());
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al actualizar el rol: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(String i) {
        rolesRepository.deleteById(i);
    }

    @Override
    public Roles findById(String i) {
        return rolesRepository.findById(i).orElse(null);
    }

    @Override
    public List<Roles> getAll(String search) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
