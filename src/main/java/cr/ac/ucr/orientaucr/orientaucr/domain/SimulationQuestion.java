package cr.ac.ucr.orientaucr.orientaucr.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "simulation_question")
public class SimulationQuestion {

    public enum Difficulty {
        easy, medium, hard
    }

    public enum QuestionCategory {
        mathematical_logic, verbal_logic, other
    }
    @Id
    @Column(name = "question_id", length = 36)
    private String questionId;

    @Column(name = "question_text", nullable = false, length = 1000)
    private String questionText;

    @Column(name = "question_category")
    @Enumerated(EnumType.STRING)
    private QuestionCategory questionCategory = QuestionCategory.other;

    @Column(name = "question_dificulty")
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty = Difficulty.medium;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SimulationOption> options = new ArrayList<>();

    public SimulationQuestion() {
    }

    public SimulationQuestion(String questionId, String questionText) {
        this.questionId = questionId;
        this.questionText = questionText;
    }
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public QuestionCategory getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(QuestionCategory questionCategory) {
        this.questionCategory = questionCategory;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public List<SimulationOption> getOptions() {
        return options;
    }

    public void setOptions(List<SimulationOption> options) {
        this.options = options;
        if (options != null) {
            for (SimulationOption option : options) {
                option.setQuestion(this);
            }
        }
    }
    public SimulationOption getCorrectOption() {
        return options.stream()
                .filter(SimulationOption::isCorrect)
                .findFirst()
                .orElse(null);
    }
}
