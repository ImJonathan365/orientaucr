package cr.ac.ucr.orientaucr.orientaucr.domain;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "simulation_question")
public class SimulationQuestion {

    public enum Difficulty {
        easy, medium, hard
    }

    @Id
    @Column(name = "question_id", length = 36)
    private String questionId;

    @Column(name = "question_text", nullable = false, length = 1000)
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_dificulty")
    private Difficulty difficulty = Difficulty.medium;

    @ManyToMany
@JoinTable(
    name = "simulation_question_category",
    joinColumns = @JoinColumn(name = "question_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id")
)
private List<Category> categories = new ArrayList<>();


    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "option_order")
    @JsonManagedReference
    private List<SimulationOption> options = new ArrayList<>();

    public SimulationQuestion() {
        this.questionId = UUID.randomUUID().toString();
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

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
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