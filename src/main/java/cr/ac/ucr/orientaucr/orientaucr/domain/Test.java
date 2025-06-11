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
    
    @Column(name = "question_help_text", columnDefinition = "TEXT")
    private String questionHelpText;
        
    @Column(name = "is_active")
    private boolean isActive;
    
    @Column(name = "is_multiple_selection")
    private boolean isMultipleSelection;

    @ManyToMany
    @JoinTable(
        name = "characteristics_question",
        joinColumns = @JoinColumn(name = "question_id"),
        inverseJoinColumns = @JoinColumn(name = "characteristics_id")
    )
    @JsonIgnoreProperties("tests")
    private List<Characteristic> characteristics = new LinkedList<>();

    public Test() {}

    public Test(String questionId, String questionText, String questionHelpText, boolean isActive, boolean isMultipleSelection) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.questionHelpText = questionHelpText;
        this.isActive = isActive;
        this.isMultipleSelection = isMultipleSelection;
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

    public String getQuestionHelpText() {
        return questionHelpText;
    }

    public void setQuestionHelpText(String questionHelpText) {
        this.questionHelpText = questionHelpText;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isIsMultipleSelection() {
        return isMultipleSelection;
    }

    public void setIsMultipleSelection(boolean isMultipleSelection) {
        this.isMultipleSelection = isMultipleSelection;
    }

    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }

}
