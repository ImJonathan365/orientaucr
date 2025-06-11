package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.SimulationOption;
import cr.ac.ucr.orientaucr.orientaucr.domain.SimulationQuestion;
import cr.ac.ucr.orientaucr.orientaucr.repository.ISimulationQuestionRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.ISimulationQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.*;

@Service
public class SimulationQuestionJPA implements ISimulationQuestionService {

    @Autowired
    private ISimulationQuestionRepository questionRepo;

    private String normalizeText(String text) {
        if (text == null) {
            return "";
        }
        return Normalizer.normalize(text.trim().toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .replaceAll("^[¿?]+", "")
                .replaceAll("[¿?]+$", "")
                .replaceAll("\\s+", " ");
    }

    @Override
    public List<SimulationQuestion> getAll(String search) {
        return new ArrayList<>(questionRepo.findAll());
    }

    @Override
    public List<SimulationQuestion> getAll() {
        return new ArrayList<>(questionRepo.findAll());
    }

    @Override
    @Transactional
    public void add(SimulationQuestion question) {
        String newText = normalizeText(question.getQuestionText());
        for (SimulationQuestion q : questionRepo.findAll()) {
            if (normalizeText(q.getQuestionText()).equals(newText)) {
                throw new IllegalArgumentException("Ya existe una pregunta con ese texto.");
            }
        }

        List<SimulationOption> options = question.getOptions();
        if (options == null || options.size() != 4) {
            throw new IllegalArgumentException("Deben ser 4 opciones.");
        }

        Set<String> normalizedOptions = new HashSet<>();
        for (SimulationOption opt : options) {
            String normOpt = normalizeText(opt.getOptionText());
            if (!normalizedOptions.add(normOpt)) {
                throw new IllegalArgumentException("No puede haber opciones repetidas.");
            }
        }

        if (options.stream().noneMatch(SimulationOption::isCorrect)) {
            throw new IllegalArgumentException("Debe marcar una opción como correcta.");
        }

        if (question.getQuestionId() == null || question.getQuestionId().isEmpty()) {
            question.setQuestionId(UUID.randomUUID().toString());
        }

        if (question.getDifficulty() == null) {
            question.setDifficulty(SimulationQuestion.Difficulty.medium);
        }

        for (SimulationOption option : options) {
            if (option.getOptionId() == null || option.getOptionId().isEmpty()) {
                option.setOptionId(UUID.randomUUID().toString());
            }
            option.setQuestion(question);
        }

        questionRepo.save(question);
    }

    @Override
    @Transactional
    public void update(SimulationQuestion question) {
        String newText = normalizeText(question.getQuestionText());
        for (SimulationQuestion q : questionRepo.findAll()) {
            if (!q.getQuestionId().equals(question.getQuestionId())
                    && normalizeText(q.getQuestionText()).equals(newText)) {
                throw new IllegalArgumentException("Ya existe una pregunta con ese texto.");
            }
        }
        List<SimulationOption> options = question.getOptions();
        if (options == null || options.size() != 4) {
            throw new IllegalArgumentException("Deben ser 4 opciones.");
        }
        Set<String> normalizedOptions = new HashSet<>();
        for (SimulationOption opt : options) {
            String normOpt = normalizeText(opt.getOptionText());
            if (!normalizedOptions.add(normOpt)) {
                throw new IllegalArgumentException("No puede haber opciones repetidas.");
            }
        }
        if (options.stream().noneMatch(SimulationOption::isCorrect)) {
            throw new IllegalArgumentException("Debe marcar una opción como correcta.");
        }
        SimulationQuestion existing = questionRepo.findById(question.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada con id: " + question.getQuestionId()));
        existing.setQuestionText(question.getQuestionText());
        existing.setQuestionCategory(question.getQuestionCategory());
        if (question.getDifficulty() == null) {
            existing.setDifficulty(SimulationQuestion.Difficulty.medium);
        } else {
            existing.setDifficulty(question.getDifficulty());
        }
        existing.getOptions().clear();
        for (SimulationOption option : options) {
            if (option.getOptionId() == null || option.getOptionId().isEmpty()) {
                option.setOptionId(UUID.randomUUID().toString());
            }
            option.setQuestion(existing);
            existing.getOptions().add(option);
        }
        questionRepo.save(existing);
    }
    
    @Override
    @Transactional
    public void deleteById(String id) {
        if (!questionRepo.existsById(id)) {
            throw new RuntimeException("No existe pregunta con id: " + id);
        }
        questionRepo.deleteById(id);
    }

    @Override
    public SimulationQuestion findById(String id) {
        return questionRepo.findById(id).orElse(null);
    }
}
