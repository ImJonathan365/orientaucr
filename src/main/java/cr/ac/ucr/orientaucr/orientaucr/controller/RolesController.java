package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.services.lRolesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    private final lRolesService service;

    public RolesController(lRolesService service) {
        this.service = service;
    }
     @PreAuthorize("hasAuthority('VER ROLES')")
    @GetMapping("/all")
    public LinkedList<Roles> getAll() {
        return new LinkedList<>(service.getAll());
    }
    @PreAuthorize("hasAuthority('VER ROLES')")
    @GetMapping("/{id}")
    public ResponseEntity<Roles> getById(@PathVariable String id) {
        Roles rol = service.findById(id);
        if (rol != null) {
            return ResponseEntity.ok(rol);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PreAuthorize("hasAuthority('CREAR ROLES')")
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Roles rol) {
        try {
           service.add(rol);
            return ResponseEntity.ok("Agregado correctamente"); // Devuelve el rol creado con sus datos
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el rol: " + e.getMessage());
        }
    }
     @PreAuthorize("hasAnyAuthority('MODIFICAR ROLES', 'EDITAR ROLES')")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Roles rol) {
        try {
            service.update(rol);
            return ResponseEntity.ok("Rol actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el rol: " + e.getMessage());
        }
    }
     @PreAuthorize("hasAuthority('ELIMINAR ROLES')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.ok("Rol eliminado correctamente.");
    }
   @PreAuthorize("hasAuthority('VER ROLES')")
    @GetMapping("/allPermissions")
    public LinkedList<Permission> getAllPermissions() {
        return new LinkedList<>(service.getAllPermissions());
    }

}
