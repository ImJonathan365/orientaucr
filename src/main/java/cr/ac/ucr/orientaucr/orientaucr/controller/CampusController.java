package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Campus;

import cr.ac.ucr.orientaucr.orientaucr.services.lCampus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.Optional;

@RestController
@RequestMapping("/api/campus")
public class CampusController {

    private final lCampus service;

    public CampusController(lCampus service) {
        this.service = service;
    }

    @GetMapping("/all")
    public LinkedList<Campus> getAll() {
        return new LinkedList<>(service.getAll());
    }

  

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Campus campus) {
        try {
            service.add(campus);
            return ResponseEntity.ok("Campus agregado correctamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody Campus campus) {
        try {
            service.update(campus);
            return ResponseEntity.ok("Campus actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.ok("Campus eliminado correctamente");
    }
}
