package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.services.lRolesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    private final lRolesService service;

    public RolesController(lRolesService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public LinkedList<Roles> getAll() {
        return new LinkedList<>(service.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Roles> getById(@PathVariable String id) {
        Roles rol = service.getRoleById(id);
        if (rol != null) {
            return ResponseEntity.ok(rol);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Roles rol) {
        try {
           service.createRole(rol);
            return ResponseEntity.ok("Agregado correctamente"); // Devuelve el rol creado con sus datos
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el rol: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Roles rol) {
        try {
            service.updateRoleWithPermissions(rol);
            return ResponseEntity.ok("Rol actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el rol: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        service.deleteRole(id);
        return ResponseEntity.ok("Rol eliminado correctamente.");
    }

    @GetMapping("/allPermissions")
    public LinkedList<Permission> getAllPermissions() {
        return new LinkedList<>(service.getAllPermissions());
    }

    // Nuevos endpoints para manejar permisos (si los necesitas)
    @GetMapping("/{id}/withPermissions")
    public ResponseEntity<Roles> getRoleWithPermissions(@PathVariable String id) {
        Roles role = service.getRoleWithPermissions(id);
        return role != null
                ? ResponseEntity.ok(role)
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/{roleId}/assignPermission/{permissionId}")
    public ResponseEntity<String> assignPermission(
            @PathVariable String roleId,
            @PathVariable String permissionId) {
        service.assignPermissionToRole(roleId, permissionId);
        return ResponseEntity.ok("Permiso asignado correctamente.");
    }

    @DeleteMapping("/{roleId}/clearPermissions")
    public ResponseEntity<String> clearPermissions(@PathVariable String roleId) {
        service.deletePermissionsFromRole(roleId);
        return ResponseEntity.ok("Permisos eliminados correctamente.");
    }
}
