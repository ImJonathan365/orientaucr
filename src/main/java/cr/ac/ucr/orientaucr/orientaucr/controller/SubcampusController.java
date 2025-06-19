package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Subcampus;
import cr.ac.ucr.orientaucr.orientaucr.services.lSubcampus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.Optional;

@RestController
@RequestMapping("/api/subcampus")
public class SubcampusController {

    private final lSubcampus service;

    public SubcampusController(lSubcampus service) {
        this.service = service;
    }
     
    @GetMapping("/all/{campusId}")
    public LinkedList<Subcampus> getAll(@PathVariable String campusId) {
        return new LinkedList<>(service.getAll(campusId));
    }


    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Subcampus subcampus) {
        try {
            service.add(subcampus);
            return ResponseEntity.ok("Subcampus agregado correctamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody Subcampus subcampus) {
        try {
            service.update(subcampus);
            return ResponseEntity.ok("Subcampus actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        service.deleteById(id); // suponiendo que internamente resuelve el SubcampusId completo
        return ResponseEntity.ok("Subcampus eliminado correctamente");
    }
}
