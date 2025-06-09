package cr.ac.ucr.orientaucr.orientaucr.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "simulation_option")
public class SimulationOption {

    @Id
    @Column(name = "option_id")
    private String optionId;

    @Column(name = "option_text")
    private String optionText;

    @Column(name = "is_correct")
    @JsonProperty("isCorrect")
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonBackReference
    private SimulationQuestion question;

 
    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public SimulationQuestion getQuestion() {
        return question;
    }

    public void setQuestion(SimulationQuestion question) {
        this.question = question;
    }
}
