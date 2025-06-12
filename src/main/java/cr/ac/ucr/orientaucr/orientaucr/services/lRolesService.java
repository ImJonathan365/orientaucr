package cr.ac.ucr.orientaucr.orientaucr.services;

import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import java.util.List;

public interface lRolesService extends CRUD<Roles> {
    List<Permission> getAllPermissions();
   
}