package cr.ac.ucr.orientaucr.orientaucr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "vocational_question")
public class Test {

    @Id
    @Column(name = "question_id", length = 36)
    private String questionId;

    @Column(name = "question_text", columnDefinition = "TEXT", nullable = false)
    private String questionText;

    @ManyToMany
    @JoinTable(
        name = "characteristics_question",
        joinColumns = @JoinColumn(name = "question_id"),
        inverseJoinColumns = @JoinColumn(name = "characteristics_id")
    )
    @JsonIgnoreProperties("tests")
    private List<Characteristic> characteristics = new LinkedList<>();

    public Test() {}

    public Test(String questionId, String questionText, List<Characteristic> characteristics) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.characteristics = characteristics;
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

    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }
}
