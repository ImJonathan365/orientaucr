package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Test;
import cr.ac.ucr.orientaucr.orientaucr.services.ITestService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('VER PREGUNTAS TEST')")
    @GetMapping("/list")
    public ResponseEntity<?> getAll() {
        try {
            List<Test> tests = service.getAll();
            if (tests == null || tests.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay preguntas registradas.");
            }
            return ResponseEntity.ok(tests);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las preguntas: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('VER PREGUNTA TEST')")
    @GetMapping("/find/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        if (id == null || id.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El ID de la pregunta es obligatorio.");
        }
        try {
            Test test = service.findById(id);
            if (test != null) {
                return ResponseEntity.ok(test);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la pregunta.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar la pregunta: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('CREAR PREGUNTAS TEST')")
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Test test) {
        if (test.getQuestionText() == null || test.getQuestionText().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Debes agregar la pregunta.");
        }
        if (test.getCharacteristics()== null || test.getCharacteristics().isEmpty()) {
            return ResponseEntity.badRequest().body("La pregunta debe tener al menos una característica.");
        }
        try {
            service.add(test);
            return ResponseEntity.ok("Pregunta agregada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar la pregunta: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('MODIFICAR PREGUNTAS TEST')")
    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody Test test) {
        if (test.getQuestionId() == null || test.getQuestionId().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El ID de la pregunta es requerido para actualizar.");
        }
        if (test.getQuestionText() == null || test.getQuestionText().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Debes agregar la pregunta.");
        }
        if (test.getCharacteristics()== null || test.getCharacteristics().isEmpty()) {
            return ResponseEntity.badRequest().body("La pregunta debe tener al menos una característica.");
        }
        try {
            Test existingTest = service.findById(test.getQuestionId());
            if (existingTest == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pregunta no encontrada.");
            }
            service.update(test);
            return ResponseEntity.ok("Pregunta actualizada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la pregunta: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ELIMINAR PREGUNTAS TEST')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        if (id == null || id.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El ID es requerido para eliminar.");
        }
        try {
            Test existingTest = service.findById(id);
            if (existingTest == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pregunta no encontrada.");
            }
            service.deleteById(id);
            return ResponseEntity.ok("Pregunta eliminada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la pregunta: " + e.getMessage());
        }
    }

}
