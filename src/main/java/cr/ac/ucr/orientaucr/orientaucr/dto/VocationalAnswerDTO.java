package cr.ac.ucr.orientaucr.orientaucr.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Respuesta del usuario a una pregunta del test vocacional.
 * Coincide con el tipo `UserTestAnswer` del frontend React.
 */
public class VocationalAnswerDTO {

    private String questionId;
    private List<String> selectedCharacteristics = new ArrayList<>();

    public VocationalAnswerDTO() {}

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public List<String> getSelectedCharacteristics() {
        return selectedCharacteristics;
    }

    public void setSelectedCharacteristics(List<String> selectedCharacteristics) {
        this.selectedCharacteristics = selectedCharacteristics;
    }
}
