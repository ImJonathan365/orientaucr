package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.Characteristic;
import cr.ac.ucr.orientaucr.orientaucr.domain.Test;
import cr.ac.ucr.orientaucr.orientaucr.repository.ITestRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.ITestService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestServiceJPA implements ITestService {

    @Autowired
    private ITestRepository repo;

    @Override
    @Transactional
    public List<Test> getAll(String search) {
        List<Object[]> result = repo.searchQuestions(search);
        return mapTestResult(result);
    }

    @Override
    @Transactional
    public List<Test> getAll() {
        List<Object[]> result = repo.getAllQuestions();
        return mapTestResult(result);
    }

    @Override
    public void add(Test t) {
        try {
            t.setQuestionId(UUID.randomUUID().toString());
            repo.addQuestion(
                    t.getQuestionId(),
                    t.getQuestionText(),
                    t.getQuestionHelpText(),
                    t.isIsActive(),
                    t.isIsMultipleSelection()
            );

            if (t.getCharacteristics() != null) {
                for (Characteristic ch : t.getCharacteristics()) {
                    repo.addCharacteristic(t.getQuestionId(), ch.getCharacteristicsId());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al agregar la pregunta.");
        }
    }

    @Override
    public void update(Test t) {
        try {
            repo.updateQuestion(
                    t.getQuestionId(),
                    t.getQuestionText(),
                    t.getQuestionHelpText(),
                    t.isIsActive(),
                    t.isIsMultipleSelection()
            );

            if (t.getCharacteristics() != null) {
                repo.deleteCharacteristics(t.getQuestionId());
                for (Characteristic ch : t.getCharacteristics()) {
                    repo.addCharacteristic(t.getQuestionId(), ch.getCharacteristicsId());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la pregunta.");
        }
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        repo.deleteCharacteristics(id);
        repo.deleteQuestion(id);
    }

    @Override
    @Transactional
    public Test findById(String id) {
        try {
            List<Object[]> result = repo.getQuestionById(id);
            if (result.isEmpty()) {
                return null;
            }

            Test test = new Test();
            List<Characteristic> characteristics = new ArrayList<>();

            if (result != null && !result.isEmpty()) {
                for (Object[] row : result) {
                    test.setQuestionId((String) row[0]);
                    test.setQuestionText((String) row[1]);
                    test.setQuestionHelpText((String) row[2]);
                    test.setIsActive((Boolean) row[3]);
                    test.setIsMultipleSelection((Boolean) row[4]);

                    Characteristic ch = new Characteristic();
                    ch.setCharacteristicsId((String) row[5]);
                    ch.setCharacteristicsName((String) row[6]);
                    ch.setCharacteristicsDescription((String) row[7]);
                    characteristics.add(ch);
                }

                test.setCharacteristics(characteristics);
            }
            return test;
        } catch (Exception e) {
            return null;
        }
    }

    private List<Test> mapTestResult(List<Object[]> result) {
        Map<String, Test> testMap = new LinkedHashMap<>();

        for (Object[] row : result) {
            String questionId = (String) row[0];
            String questionText = (String) row[1];
            String helpText = (String) row[2];
            Boolean isActive = (Boolean) row[3];
            Boolean isMultipleSelection = (Boolean) row[4];
            String charId = (String) row[5];
            String charName = (String) row[6];
            String charDesc = (String) row[7];

            Test test = testMap.get(questionId);
            if (test == null) {
                test = new Test();
                test.setQuestionId(questionId);
                test.setQuestionText(questionText);
                test.setQuestionHelpText(helpText);
                test.setIsActive(isActive);
                test.setIsMultipleSelection(isMultipleSelection);
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

}
