package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.Characteristic;
import cr.ac.ucr.orientaucr.orientaucr.domain.Test;
import cr.ac.ucr.orientaucr.orientaucr.services.ITestService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class TestServiceJPA implements ITestService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Test> getAll(String search) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("sp_search_vocational_questions")
                .registerStoredProcedureParameter("p_search", String.class, ParameterMode.IN)
                .setParameter("p_search", search);

        List<Object[]> result = query.getResultList();
        Map<String, Test> testMap = new LinkedHashMap<>();

        for (Object[] row : result) {
            String questionId = (String) row[0];
            String questionText = (String) row[1];
            String charId = (String) row[2];
            String charName = (String) row[3];
            String charDesc = (String) row[4];

            Test test = testMap.get(questionId);
            if (test == null) {
                test = new Test();
                test.setQuestionId(questionId);
                test.setQuestionText(questionText);
                test.setCharacteristics(new ArrayList<>());
                testMap.put(questionId, test);
            }

            Characteristic characteristic = new Characteristic();
            characteristic.setCharacteristicsId(charId);
            characteristic.setCharacteristicsName(charName);
            characteristic.setCharacteristicsDescription(charDesc);
            test.getCharacteristics().add(characteristic);
        }

        return new ArrayList<>(testMap.values());
    }

    @Override
    public List<Test> getAll() {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("sp_get_vocational_test_questions");

        List<Object[]> result = query.getResultList();
        Map<String, Test> testMap = new LinkedHashMap<>();

        for (Object[] row : result) {
            String questionId = (String) row[0];
            String questionText = (String) row[1];
            String charId = (String) row[2];
            String charName = (String) row[3];
            String charDesc = (String) row[4];

            Test test = testMap.get(questionId);
            if (test == null) {
                test = new Test();
                test.setQuestionId(questionId);
                test.setQuestionText(questionText);
                test.setCharacteristics(new ArrayList<>());
                testMap.put(questionId, test);
            }

            Characteristic characteristic = new Characteristic();
            characteristic.setCharacteristicsId(charId);
            characteristic.setCharacteristicsName(charName);
            characteristic.setCharacteristicsDescription(charDesc);

            test.getCharacteristics().add(characteristic);
        }

        return new ArrayList<>(testMap.values());
    }

    @Override
    public void add(Test t) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("sp_add_vocational_question")
                .registerStoredProcedureParameter("p_question_id", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_question_text", String.class, ParameterMode.IN)
                .setParameter("p_question_id", t.getQuestionId())
                .setParameter("p_question_text", t.getQuestionText());
        query.execute();

        for (Characteristic ch : t.getCharacteristics()) {
            StoredProcedureQuery chQuery = entityManager
                    .createStoredProcedureQuery("sp_add_characteristic_to_question")
                    .registerStoredProcedureParameter("p_question_id", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_characteristics_id", String.class, ParameterMode.IN)
                    .setParameter("p_question_id", t.getQuestionId())
                    .setParameter("p_characteristics_id", ch.getCharacteristicsId());
            chQuery.execute();
        }
    }

    @Override
    public void update(Test t) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("sp_update_vocational_question")
                .registerStoredProcedureParameter("p_question_id", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_question_text", String.class, ParameterMode.IN)
                .setParameter("p_question_id", t.getQuestionId())
                .setParameter("p_question_text", t.getQuestionText());
        query.execute();

        StoredProcedureQuery deleteCh = entityManager
                .createStoredProcedureQuery("sp_delete_characteristics_from_question")
                .registerStoredProcedureParameter("p_question_id", String.class, ParameterMode.IN)
                .setParameter("p_question_id", t.getQuestionId());
        deleteCh.execute();

        for (Characteristic ch : t.getCharacteristics()) {
            StoredProcedureQuery chQuery = entityManager
                    .createStoredProcedureQuery("sp_add_characteristic_to_question")
                    .registerStoredProcedureParameter("p_question_id", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_characteristics_id", String.class, ParameterMode.IN)
                    .setParameter("p_question_id", t.getQuestionId())
                    .setParameter("p_characteristics_id", ch.getCharacteristicsId());
            chQuery.execute();
        }
    }

    @Override
    public void deleteById(String id) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("sp_delete_vocational_question")
                .registerStoredProcedureParameter("p_question_id", String.class, ParameterMode.IN)
                .setParameter("p_question_id", id);
        query.execute();
    }

    @Override
    public Test findById(String id) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("sp_get_vocational_question_by_id")
                .registerStoredProcedureParameter("p_question_id", String.class, ParameterMode.IN)
                .setParameter("p_question_id", id);

        List<Object[]> result = query.getResultList();

        if (result.isEmpty()) {
            return null;
        }

        Test test = new Test();
        List<Characteristic> characteristics = new ArrayList<>();

        for (Object[] row : result) {
            test.setQuestionId((String) row[0]);
            test.setQuestionText((String) row[1]);

            Characteristic ch = new Characteristic();
            ch.setCharacteristicsId((String) row[2]);
            ch.setCharacteristicsName((String) row[3]);
            ch.setCharacteristicsDescription((String) row[4]);
            characteristics.add(ch);
        }

        test.setCharacteristics(characteristics);
        return test;
    }
}
