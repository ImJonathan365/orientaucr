package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.dto.VocationalRecommendationRequest;
import cr.ac.ucr.orientaucr.orientaucr.dto.VocationalRecommendationResponse;
import cr.ac.ucr.orientaucr.orientaucr.services.IVocationalRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Expone la recomendacion vocacional al frontend. Internamente delega el calculo
 * al microservicio FastAPI y persiste el resultado del usuario autenticado.
 */
@RestController
@RequestMapping("/api/vocational")
public class VocationalRecommendationController {

    @Autowired
    private IVocationalRecommendationService service;

    @PostMapping("/recommend")
    public ResponseEntity<?> recommend(@RequestBody VocationalRecommendationRequest request) {
        if (request == null || request.getAnswers() == null || request.getAnswers().isEmpty()) {
            return ResponseEntity.badRequest().body("Debe enviar al menos una respuesta del test.");
        }
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            if (email == null || email.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado.");
            }
            VocationalRecommendationResponse response = service.recommendForUser(email, request);
            if (response == null) {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body("El servicio de recomendacion no devolvio resultados.");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al generar la recomendacion: " + e.getMessage());
        }
    }

    /** Reentrena el modelo (uso administrativo). */
    @PreAuthorize("hasAuthority('CREAR CARRERAS')")
    @PostMapping("/retrain")
    public ResponseEntity<?> retrain() {
        try {
            return ResponseEntity.ok(service.retrain());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al reentrenar el modelo: " + e.getMessage());
        }
    }
}
