package cr.ac.ucr.orientaucr.orientaucr.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "simulation_question")
public class SimulationQuestion {

    @Id
    @Column(name = "question_id", length = 36)
    private String questionId;

    @Column(name = "question_text", nullable = false, length = 1000)
    private String questionText;

    @Column(name = "question_category")
    @Enumerated(EnumType.STRING)
    private QuestionCategory questionCategory = QuestionCategory.other;

    // Relación uno a muchos: una pregunta tiene muchas opciones
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SimulationOption> options = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "correct_option_id")
private SimulationOption correctOption;


    public SimulationQuestion() {
    }

    public SimulationQuestion(String questionId, String questionText) {
        this.questionId = questionId;
        this.questionText = questionText;
    }

    // Getters y Setters

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
        return correctOption;
    }

    public void setCorrectOption(SimulationOption correctOption) {
        this.correctOption = correctOption;
    }
}
