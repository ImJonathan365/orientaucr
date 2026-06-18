package cr.ac.ucr.orientaucr.orientaucr.services;

import cr.ac.ucr.orientaucr.orientaucr.dto.VocationalRecommendationRequest;
import cr.ac.ucr.orientaucr.orientaucr.dto.VocationalRecommendationResponse;

public interface IVocationalRecommendationService {

    /**
     * Obtiene la recomendacion para el usuario autenticado (por email) y
     * persiste el resultado.
     */
    VocationalRecommendationResponse recommendForUser(String userEmail, VocationalRecommendationRequest request);

    /** Recarga el dominio desde la BD y reentrena el modelo en FastAPI. */
    String retrain();
}
