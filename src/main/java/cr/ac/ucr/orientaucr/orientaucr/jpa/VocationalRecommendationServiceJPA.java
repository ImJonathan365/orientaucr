package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.client.VocationalServiceClient;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import cr.ac.ucr.orientaucr.orientaucr.dto.CareerRecommendationDTO;
import cr.ac.ucr.orientaucr.orientaucr.dto.VocationalRecommendationRequest;
import cr.ac.ucr.orientaucr.orientaucr.dto.VocationalRecommendationResponse;
import cr.ac.ucr.orientaucr.orientaucr.repository.IVocationalResultRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.IUserService;
import cr.ac.ucr.orientaucr.orientaucr.services.IVocationalRecommendationService;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VocationalRecommendationServiceJPA implements IVocationalRecommendationService {

    private static final Logger log = LoggerFactory.getLogger(VocationalRecommendationServiceJPA.class);

    @Autowired
    private VocationalServiceClient client;

    @Autowired
    private IUserService userService;

    @Autowired
    private IVocationalResultRepository resultRepository;

    @Override
    public VocationalRecommendationResponse recommendForUser(String userEmail, VocationalRecommendationRequest request) {
        VocationalRecommendationResponse response = client.recommend(request);

        Optional<User> userOptional = userService.findByEmail(userEmail);
        if (userOptional.isPresent() && response != null) {
            persistResult(userOptional.get().getUserId(), response);
        }
        return response;
    }

    /** Persistencia best-effort: si falla, no rompe la recomendacion. */
    private void persistResult(String userId, VocationalRecommendationResponse response) {
        try {
            String personality = response.getRecommendations().isEmpty()
                    ? "Sin recomendacion"
                    : response.getRecommendations().get(0).getCareerName();
            String analysis = response.getExplanation().stream().collect(Collectors.joining("; "));

            resultRepository.saveResult(UUID.randomUUID().toString(), userId, personality, analysis);

            for (CareerRecommendationDTO rec : response.getRecommendations()) {
                int score = (int) Math.round(rec.getScore() * 100);
                resultRepository.saveUserCareer(userId, rec.getCareerId(), score);
            }
        } catch (Exception e) {
            log.warn("No se pudo persistir el resultado vocacional del usuario {}: {}", userId, e.getMessage());
        }
    }

    @Override
    public String retrain() {
        return client.retrain();
    }
}
