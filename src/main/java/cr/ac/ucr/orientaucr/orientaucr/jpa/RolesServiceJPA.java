package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import cr.ac.ucr.orientaucr.orientaucr.repository.CustomRolesRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.lRolesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import cr.ac.ucr.orientaucr.orientaucr.repository.IPermissionRepository;
import cr.ac.ucr.orientaucr.orientaucr.repository.IRolesRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.lRolesService;

@Service
public class RolesServiceJPA implements lRolesService {

    private final IRolesRepository rolesRepository;
    private final IPermissionRepository permissionRepository;
    private final CustomRolesRepository customRepository;

    @Autowired
    public RolesServiceJPA(IRolesRepository rolesRepository, IPermissionRepository permissionRepository, cr.ac.ucr.orientaucr.orientaucr.repository.CustomRolesRepository customRepository) {
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
    public void add(Roles t) {
        String rolId=UUID.randomUUID().toString();
      try {
            if (t.getRolId() == null) {
                t.setRolId(rolId);
            }
            customRepository.addRole( rolId,t.getRolName());
            if (t.getPermissions() != null && !t.getPermissions().isEmpty()) {
                for (Permission permission : t.getPermissions()) {
                    customRepository.assignPermissionToRole(rolId, permission.getPermissionId());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al crear el rol: " + e.getMessage(), e);
        }
    }


    @Override
    public void update(Roles t) {
       try {
            customRepository.updateRole(t.getRolId(), t.getRolName());
            customRepository.deletePermissionsFromRole(t.getRolId());
            if (t.getPermissions() != null && !t.getPermissions().isEmpty()) {
                for (Permission permission : t.getPermissions()) {
                    customRepository.assignPermissionToRole(
                            t.getRolId(),
                            permission.getPermissionId()
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el rol: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(String i) {
        rolesRepository.deleteById(i);
    }

    @Override
    public Roles findById(String i) {
    return rolesRepository.findById(i).orElse(null);    }

    @Override
    public List<Roles> getAll(String search) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
