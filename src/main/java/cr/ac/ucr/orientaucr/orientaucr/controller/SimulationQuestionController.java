package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Category;
import cr.ac.ucr.orientaucr.orientaucr.domain.SimulationAttempt;
import cr.ac.ucr.orientaucr.orientaucr.domain.SimulationQuestion;
import cr.ac.ucr.orientaucr.orientaucr.jpa.SimulationAttemptJPA;
import cr.ac.ucr.orientaucr.orientaucr.services.ISimulationQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/questions")
public class SimulationQuestionController {

    @Autowired
    private ISimulationQuestionService questionService;
    @Autowired
    private SimulationAttemptJPA attemptService;

    @PreAuthorize("hasAuthority('VER PREGUNTAS SIMULADAS')")
    @GetMapping
    public ResponseEntity<List<SimulationQuestion>> getAll(@RequestParam(required = false) String search) {
        List<SimulationQuestion> questions
                = (search != null && !search.isEmpty()) ? questionService.getAll(search) : questionService.getAll();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimulationQuestion> getById(@PathVariable String id) {
        SimulationQuestion question = questionService.findById(id);
        return (question == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(question);
    }

    @PreAuthorize("hasAuthority('CREAR PREGUNTAS SIMULADAS')")
    @PostMapping
    public ResponseEntity<String> create(@RequestBody SimulationQuestion question) {
        try {
            if (question.getDifficulty() == null) {
                question.setDifficulty(SimulationQuestion.Difficulty.medium);
            }
            questionService.add(question);
            return ResponseEntity.ok("Pregunta creada exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('MODIFICAR PREGUNTAS SIMULADAS')")
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody SimulationQuestion question) {
        if (!id.equals(question.getQuestionId())) {
            return ResponseEntity.badRequest().body("El ID de la URL no coincide con el ID del objeto");
        }
        try {
            if (question.getDifficulty() == null) {
                question.setDifficulty(SimulationQuestion.Difficulty.medium);
            }
            questionService.update(question);
            return ResponseEntity.ok("Pregunta actualizada exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('ELIMINAR PREGUNTAS SIMULADAS')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        try {
            questionService.deleteById(id);
            return ResponseEntity.ok("Pregunta eliminada exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

   @PreAuthorize("hasAuthority('VER PREGUNTAS SIMULADAS')")
@GetMapping("/simulation-exam")
public ResponseEntity<List<SimulationQuestion>> getSimulationExam() {
    List<SimulationQuestion> allQuestions = questionService.getAll();
    Map<String, List<SimulationQuestion>> categoryMap = new HashMap<>();
    for (SimulationQuestion q : allQuestions) {
        if (q.getCategories() != null) {
            for (Category cat : q.getCategories()) {
                categoryMap.computeIfAbsent(cat.getCategoryName(), k -> new ArrayList<>()).add(q);
            }
        }
    }
    int totalQuestions = 30; 
    int numCategories = categoryMap.size();
    int perCategory = numCategories > 0 ? totalQuestions / numCategories : 0;

    List<SimulationQuestion> exam = new ArrayList<>();
    for (List<SimulationQuestion> questions : categoryMap.values()) {
        Collections.shuffle(questions);
        exam.addAll(questions.stream().limit(perCategory).toList());
    }
    if (exam.size() < totalQuestions) {
        List<SimulationQuestion> restantes = new ArrayList<>(allQuestions);
        restantes.removeAll(exam);
        Collections.shuffle(restantes);
        exam.addAll(restantes.stream().limit(totalQuestions - exam.size()).toList());
    }

    Collections.shuffle(exam);
    return ResponseEntity.ok(exam);
}

    @PreAuthorize("hasAuthority('VER PREGUNTAS SIMULADAS')")
    @PostMapping("/submit-exam")
    public ResponseEntity<String> submitExam(@RequestBody SimulationAttempt attempt) {
        if (attempt.getAttemptScore() < 0 || attempt.getAttemptScore() > 100 || attempt.getUserId() == null || attempt.getUserId().isBlank()) {
            return ResponseEntity.badRequest().body("Datos inválidos del intento.");
        }
        attempt.setAttemptId(UUID.randomUUID().toString());
        attempt.setTakenAt(LocalDateTime.now());
        attemptService.saveAttempt(attempt);
        return ResponseEntity.ok("Intento guardado correctamente.");
    }
}