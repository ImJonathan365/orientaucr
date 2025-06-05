package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Test;
import cr.ac.ucr.orientaucr.orientaucr.services.TestService;
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
@RequestMapping("/api/test")
public class TestController {
    
    private final TestService service = new TestService();

    @GetMapping("/all")
    public LinkedList<Test> getAll() {
        return service.getAllTest();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Test> getById(@PathVariable String id) {
        Test test = service.findTestById(id);
        if (test != null) {
            return ResponseEntity.ok(test);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Test test) {
        service.addTest(test);
        return ResponseEntity.ok("Pregunta agregada correctamente.");
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody Test test) {
        service.updateTest(test);
        return ResponseEntity.ok("Pregunta actualizada correctamente.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        service.deleteTestById(id);
        return ResponseEntity.ok("Pregunta eliminada correctamente.");
    }
    
}