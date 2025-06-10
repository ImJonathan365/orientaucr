package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.SimulationAttempt;
import cr.ac.ucr.orientaucr.orientaucr.domain.SimulationQuestion;
import cr.ac.ucr.orientaucr.orientaucr.jpa.SimulationAttemptJPA;
import cr.ac.ucr.orientaucr.orientaucr.services.ISimulationQuestionService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/questions")
public class SimulationQuestionController {

    @Autowired
    private ISimulationQuestionService questionService;
    @Autowired
    private SimulationAttemptJPA attemptService;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        try {
            questionService.deleteById(id);
            return ResponseEntity.ok("Pregunta eliminada exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/simulation-exam")
    public ResponseEntity<List<SimulationQuestion>> getSimulationExam(){
        List<SimulationQuestion> allQuestions = questionService.getAll();
        List<SimulationQuestion> verbal = new ArrayList<>();
        List<SimulationQuestion> math = new ArrayList<>();
        for (SimulationQuestion q : allQuestions){
            if (q.getQuestionCategory() != null){
                switch (q.getQuestionCategory()){
                    case verbal_logic ->
                        verbal.add(q);
                    case mathematical_logic ->
                        math.add(q);
                    default -> {
                    }}}}
        Collections.shuffle(verbal);
        Collections.shuffle(math);
        List<SimulationQuestion> exam = new ArrayList<>();
        exam.addAll(verbal.stream().limit(15).toList());
        exam.addAll(math.stream().limit(15).toList());
        Collections.shuffle(exam);
        return ResponseEntity.ok(exam);
    }

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