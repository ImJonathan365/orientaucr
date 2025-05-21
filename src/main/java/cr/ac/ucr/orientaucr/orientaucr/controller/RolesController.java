package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.domain.Test;
import cr.ac.ucr.orientaucr.orientaucr.service.RolesService;
import cr.ac.ucr.orientaucr.orientaucr.service.TestService;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/roles")
public class RolesController {

   private final RolesService service = new RolesService();

    @GetMapping("/all")
    public LinkedList<Roles> getAll() {
        return service.getAllTest();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Roles> getById(@PathVariable String id) {
        Roles rol = service.findTestById(id);
        if (rol != null) {
            return ResponseEntity.ok(rol);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Roles rol) {
        service.addTest(rol);
        return ResponseEntity.ok("Pregunta agregada correctamente.");
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody Roles rol) {
        service.updateTest(rol);
        return ResponseEntity.ok("Pregunta actualizada correctamente.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        service.deleteTestById(id);
        return ResponseEntity.ok("Pregunta eliminada correctamente.");
    }
}
