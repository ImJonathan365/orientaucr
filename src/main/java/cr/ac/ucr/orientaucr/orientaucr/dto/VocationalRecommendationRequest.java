package cr.ac.ucr.orientaucr.orientaucr.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Solicitud que el frontend envia a Spring Boot para obtener una recomendacion.
 * Spring la traduce al formato del microservicio FastAPI.
 */
public class VocationalRecommendationRequest {

    private List<VocationalAnswerDTO> answers = new ArrayList<>();
    private int topN = 3;

    public VocationalRecommendationRequest() {}

    public List<VocationalAnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<VocationalAnswerDTO> answers) {
        this.answers = answers;
    }

    public int getTopN() {
        return topN;
    }

    public void setTopN(int topN) {
        this.topN = topN;
    }
}
