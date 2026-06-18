package cr.ac.ucr.orientaucr.orientaucr.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Respuesta del microservicio FastAPI. Los nombres JSON (snake_case) coinciden
 * con la salida de FastAPI; este mismo objeto se devuelve al frontend.
 */
public class VocationalRecommendationResponse {

    @JsonProperty("recommendations")
    private List<CareerRecommendationDTO> recommendations = new ArrayList<>();

    @JsonProperty("explanation")
    private List<String> explanation = new ArrayList<>();

    @JsonProperty("profile_vector")
    private Map<String, Double> profileVector = new LinkedHashMap<>();

    public VocationalRecommendationResponse() {}

    public List<CareerRecommendationDTO> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<CareerRecommendationDTO> recommendations) {
        this.recommendations = recommendations;
    }

    public List<String> getExplanation() {
        return explanation;
    }

    public void setExplanation(List<String> explanation) {
        this.explanation = explanation;
    }

    public Map<String, Double> getProfileVector() {
        return profileVector;
    }

    public void setProfileVector(Map<String, Double> profileVector) {
        this.profileVector = profileVector;
    }
}
