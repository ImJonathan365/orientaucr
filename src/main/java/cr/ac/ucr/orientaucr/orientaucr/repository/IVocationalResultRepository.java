package cr.ac.ucr.orientaucr.orientaucr.repository;

import cr.ac.ucr.orientaucr.orientaucr.domain.VocationalUserResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

/**
 * Persistencia de los resultados del test vocacional mediante procedimientos
 * almacenados (mismo patron que el resto del proyecto).
 */
public interface IVocationalResultRepository extends JpaRepository<VocationalUserResult, String> {

    @Procedure(procedureName = "sp_save_vocational_result")
    void saveResult(
        @Param("p_result_id") String resultId,
        @Param("p_user_id") String userId,
        @Param("p_personality") String personality,
        @Param("p_analysis") String analysis
    );

    @Procedure(procedureName = "sp_save_vocational_user_career")
    void saveUserCareer(
        @Param("p_user_id") String userId,
        @Param("p_career_id") String careerId,
        @Param("p_score") int score
    );
}
