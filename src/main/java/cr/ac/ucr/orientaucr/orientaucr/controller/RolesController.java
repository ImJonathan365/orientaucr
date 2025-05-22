package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.service.RolesService;

import java.util.LinkedList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/roles")
public class RolesController {

   private final RolesService service = new RolesService();

    @GetMapping("/all")
    public LinkedList<Roles> getAll() {
        return service.getAllRoles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Roles> getById(@PathVariable String id) {
        Roles rol = service.findById(id);
        if (rol != null) {
            return ResponseEntity.ok(rol);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Roles rol) {
        service.add(rol);
        return ResponseEntity.ok("Pregunta agregada correctamente.");
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody Roles rol) {
        service.update(rol);
        return ResponseEntity.ok("Pregunta actualizada correctamente.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.ok("Pregunta eliminada correctamente.");
    }
     @GetMapping("/allPermissions")
    public LinkedList<Permission> getAllPermissions() {
        return service.getAllPermissions();
    }

}
