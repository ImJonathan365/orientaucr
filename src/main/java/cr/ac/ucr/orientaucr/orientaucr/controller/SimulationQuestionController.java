package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.SimulationQuestion;
import cr.ac.ucr.orientaucr.orientaucr.services.ISimulationQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class SimulationQuestionController {

    @Autowired
    private ISimulationQuestionService questionService;

    @GetMapping
    public ResponseEntity<List<SimulationQuestion>> getAll(@RequestParam(required = false) String search) {
        List<SimulationQuestion> questions = 
            (search != null && !search.isEmpty()) ? questionService.getAll(search) : questionService.getAll();

        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimulationQuestion> getById(@PathVariable String id) {
        SimulationQuestion question = questionService.findById(id);
        return (question == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(question);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody SimulationQuestion question) {
        try {
            questionService.add(question);
            return ResponseEntity.ok("Pregunta creada exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody SimulationQuestion question) {
        if (!id.equals(question.getQuestionId())) {
            return ResponseEntity.badRequest().body("El ID de la URL no coincide con el ID del objeto");
        }

        try {
            questionService.update(question);
            return ResponseEntity.ok("Pregunta actualizada exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        try {
            questionService.deleteById(id);
            return ResponseEntity.ok("Pregunta eliminada exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
