package cr.ac.ucr.orientaucr.orientaucr.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Una carrera recomendada por el microservicio FastAPI.
 * Los nombres JSON (snake_case) coinciden con la respuesta de FastAPI.
 */
public class CareerRecommendationDTO {

    @JsonProperty("career_id")
    private String careerId;

    @JsonProperty("career_name")
    private String careerName;

    @JsonProperty("score")
    private double score;

    public CareerRecommendationDTO() {}

    public String getCareerId() {
        return careerId;
    }

    public void setCareerId(String careerId) {
        this.careerId = careerId;
    }

    public String getCareerName() {
        return careerName;
    }

    public void setCareerName(String careerName) {
        this.careerName = careerName;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
