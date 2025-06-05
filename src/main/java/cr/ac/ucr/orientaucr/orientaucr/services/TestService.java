package cr.ac.ucr.orientaucr.orientaucr.services;

import cr.ac.ucr.orientaucr.orientaucr.dao_implements.TestDAOImplements;
import cr.ac.ucr.orientaucr.orientaucr.domain.Test;
import java.util.LinkedList;

public class TestService {
    
    private TestDAOImplements data = new TestDAOImplements();

    public TestService() {}
    
    public LinkedList<Test> searchTest(String search){
        return data.getAll(search);
    }
    
    public LinkedList<Test> getAllTest(){
        return data.getAll();
    }
    
    public void addTest(Test t) {
        data.add(t);
    }
    
    public void updateTest(Test t) {
        data.update(t);
    }
    
    public void deleteTestById(String id) {
        data.deleteById(id);
    }
    
    public Test findTestById(String id) {
        return data.findById(id);
    }
    
}
