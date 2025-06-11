package cr.ac.ucr.orientaucr.orientaucr.repository;

import cr.ac.ucr.orientaucr.orientaucr.domain.Test;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface ITestRepository extends JpaRepository<Test, String> {

    @Procedure(procedureName = "sp_get_vocational_test_questions")
    List<Object[]> getAllQuestions();

    @Procedure(procedureName = "sp_search_vocational_questions")
    List<Object[]> searchQuestions(@Param("p_search") String search);

    @Procedure(procedureName = "sp_add_vocational_question")
    void addQuestion(
        @Param("p_question_id") String id,
        @Param("p_question_text") String text,
        @Param("p_help_text") String helpText,
        @Param("p_is_active") boolean isActive,
        @Param("p_is_multiple_selection") boolean isMultipleSelection
    );

    @Procedure(procedureName = "sp_add_characteristic_to_question")
    void addCharacteristic(
        @Param("p_question_id") String questionId,
        @Param("p_characteristics_id") String characteristicId
    );

    @Procedure(procedureName = "sp_update_vocational_question")
    void updateQuestion(
        @Param("p_question_id") String id,
        @Param("p_question_text") String text,
        @Param("p_help_text") String helpText,
        @Param("p_is_active") boolean isActive,
        @Param("p_is_multiple_selection") boolean isMultipleSelection
    );

    @Procedure(procedureName = "sp_delete_characteristics_from_question")
    void deleteCharacteristics(@Param("p_question_id") String id);

    @Procedure(procedureName = "sp_delete_vocational_question")
    void deleteQuestion(@Param("p_question_id") String id);

    @Procedure(procedureName = "sp_get_vocational_question_by_id")
    List<Object[]> getQuestionById(@Param("p_question_id") String id);
    
}
