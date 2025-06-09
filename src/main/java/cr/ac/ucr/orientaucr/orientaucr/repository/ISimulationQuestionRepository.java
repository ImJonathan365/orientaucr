
package cr.ac.ucr.orientaucr.orientaucr.repository;
import cr.ac.ucr.orientaucr.orientaucr.domain.SimulationQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author luisr
 */
@Repository
public interface ISimulationQuestionRepository extends JpaRepository<SimulationQuestion, String> {
    
}
