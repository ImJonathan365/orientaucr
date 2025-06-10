package cr.ac.ucr.orientaucr.orientaucr.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author luisr
 */

@Entity
@Table(name = "simulation_attempt")
public class SimulationAttempt {

    @Id
    @Column(name = "attempt_id", columnDefinition = "CHAR(36)")
    private String attemptId = UUID.randomUUID().toString();

    @Column(name = "attempt_score", nullable = false)
    private double attemptScore;

    @Column(name = "taken_at", columnDefinition = "DATETIME")
    private LocalDateTime takenAt = LocalDateTime.now();

    @Column(name = "user_id", columnDefinition = "CHAR(36)")
    private String userId;

    public SimulationAttempt() {
    }
    
    public SimulationAttempt(double attemptScore, String userId) {
        this.attemptScore = attemptScore;
        this.userId = userId;
    }

    public String getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(String attemptId) {
        this.attemptId = attemptId;
    }

    public double getAttemptScore() { 
        return attemptScore;
    }

    public void setAttemptScore(double attemptScore) {
        this.attemptScore = attemptScore;
    }

    public LocalDateTime getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(LocalDateTime takenAt) {
        this.takenAt = takenAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}