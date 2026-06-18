package cr.ac.ucr.orientaucr.orientaucr.client;

import cr.ac.ucr.orientaucr.orientaucr.dto.VocationalAnswerDTO;
import cr.ac.ucr.orientaucr.orientaucr.dto.VocationalRecommendationRequest;
import cr.ac.ucr.orientaucr.orientaucr.dto.VocationalRecommendationResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Cliente HTTP hacia el microservicio FastAPI de recomendacion vocacional.
 * Traduce los DTOs de Spring al formato (snake_case) que espera FastAPI.
 */
@Component
public class VocationalServiceClient {

    private static final Logger log = LoggerFactory.getLogger(VocationalServiceClient.class);

    private final RestClient restClient;

    public VocationalServiceClient(@Value("${vocational.service.url}") String baseUrl) {
        // SimpleClientHttpRequestFactory (HttpURLConnection) envia el cuerpo de
        // forma confiable. El cliente HTTP por defecto del JDK que elige RestClient
        // cuando no hay Apache HttpClient en el classpath puede enviar el body vacio.
        this.restClient = RestClient.builder()
                .requestFactory(new SimpleClientHttpRequestFactory())
                .baseUrl(baseUrl)
                .build();
    }

    /** Envia las respuestas del test y devuelve el ranking de carreras. */
    public VocationalRecommendationResponse recommend(VocationalRecommendationRequest request) {
        List<Map<String, Object>> answers = new ArrayList<>();
        if (request.getAnswers() != null) {
            for (VocationalAnswerDTO answer : request.getAnswers()) {
                Map<String, Object> item = new HashMap<>();
                item.put("question_id", answer.getQuestionId());
                item.put("selected_characteristics", answer.getSelectedCharacteristics());
                answers.add(item);
            }
        }

        Map<String, Object> body = new HashMap<>();
        body.put("answers", answers);
        body.put("top_n", request.getTopN());

        log.info("[Vocational] Enviando a FastAPI: {} respuestas, top_n={}", answers.size(), request.getTopN());

        return restClient.post()
                .uri("/recomendar")
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(VocationalRecommendationResponse.class);
    }

    /** Solicita a FastAPI recargar el dominio desde la BD y reentrenar el modelo. */
    public String retrain() {
        return restClient.post()
                .uri("/retrain")
                .retrieve()
                .body(String.class);
    }
}
