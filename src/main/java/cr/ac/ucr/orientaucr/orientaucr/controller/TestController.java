package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Test;
import cr.ac.ucr.orientaucr.orientaucr.services.ITestService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/test")
public class TestController {
    
    @Autowired
    private ITestService service;

    @GetMapping("/all")
    public List<Test> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Test> getById(@PathVariable String id) {
        Test test = service.findById(id);
        if (test != null) {
            return ResponseEntity.ok(test);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Test test) {
        service.add(test);
        return ResponseEntity.ok("Pregunta agregada correctamente.");
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody Test test) {
        service.update(test);
        return ResponseEntity.ok("Pregunta actualizada correctamente.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.ok("Pregunta eliminada correctamente.");
    }
    
}