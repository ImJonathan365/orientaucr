package cr.ac.ucr.orientaucr.orientaucr.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 * Resultado del test vocacional de un usuario (tabla `vocational_user_result`).
 * Sirve como ancla JPA para el repositorio que invoca los procedimientos
 * almacenados de persistencia.
 */
@Entity
@Table(name = "vocational_user_result")
public class VocationalUserResult {

    @Id
    @Column(name = "result_id", length = 36)
    private String resultId;

    @Column(name = "result_personality", columnDefinition = "TEXT", nullable = false)
    private String resultPersonality;

    @Column(name = "result_analisys", columnDefinition = "TEXT")
    private String resultAnalisys;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "user_id", length = 36)
    private String userId;

    public VocationalUserResult() {}

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getResultPersonality() {
        return resultPersonality;
    }

    public void setResultPersonality(String resultPersonality) {
        this.resultPersonality = resultPersonality;
    }

    public String getResultAnalisys() {
        return resultAnalisys;
    }

    public void setResultAnalisys(String resultAnalisys) {
        this.resultAnalisys = resultAnalisys;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
