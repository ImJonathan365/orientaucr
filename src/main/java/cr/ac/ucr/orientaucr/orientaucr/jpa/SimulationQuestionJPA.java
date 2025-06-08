package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.SimulationOption;
import cr.ac.ucr.orientaucr.orientaucr.domain.SimulationQuestion;
import cr.ac.ucr.orientaucr.orientaucr.repository.ISimulationQuestionRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.ISimulationQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SimulationQuestionJPA implements ISimulationQuestionService {

    @Autowired
    private ISimulationQuestionRepository questionRepo;

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
        if (question.getQuestionId() == null || question.getQuestionId().isEmpty()) {
            question.setQuestionId(UUID.randomUUID().toString());
        }

        List<SimulationOption> options = question.getOptions();
        if (options == null || options.isEmpty()) {
            throw new IllegalArgumentException("La pregunta debe tener opciones");
        }

        SimulationOption correct = null;
        for (SimulationOption option : options) {
            if (option.getOptionId() == null || option.getOptionId().isEmpty()) {
                option.setOptionId(UUID.randomUUID().toString());
            }
            option.setQuestion(question);
            if (option.isCorrect()) {
                correct = option;
            }
        }

        if (correct == null) {
            throw new IllegalArgumentException("Debe haber al menos una opción marcada como correcta");
        }

        question.setCorrectOption(correct);
        questionRepo.save(question);
    }

    @Override
    @Transactional
    public void update(SimulationQuestion question) {
        SimulationQuestion existing = questionRepo.findById(question.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada con id: " + question.getQuestionId()));

        existing.setQuestionText(question.getQuestionText());
        existing.getOptions().clear();

        List<SimulationOption> newOptions = question.getOptions();
        if (newOptions == null || newOptions.isEmpty()) {
            throw new IllegalArgumentException("La pregunta debe tener opciones");
        }

        SimulationOption correct = null;
        for (SimulationOption option : newOptions) {
            if (option.getOptionId() == null || option.getOptionId().isEmpty()) {
                option.setOptionId(UUID.randomUUID().toString());
            }
            option.setQuestion(existing);
            existing.getOptions().add(option);

            if (option.isCorrect()) {
                correct = option;
            }
        }

        if (correct == null) {
            throw new IllegalArgumentException("Debe haber al menos una opción marcada como correcta");
        }

        existing.setCorrectOption(correct);
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
