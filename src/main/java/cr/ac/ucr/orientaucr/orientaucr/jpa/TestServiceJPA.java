package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.Test;
import cr.ac.ucr.orientaucr.orientaucr.repository.ITestRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.ITestService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceJPA implements ITestService {

    @Autowired
    private ITestRepository repo;
            
    @Override
    public List<Test> getAll(String search) {
        return null;
    }

    @Override
    public List<Test> getAll() {
        return null;
    }

    @Override
    public void add(Test t) {

    }

    @Override
    public void update(Test t) {

    }

    @Override
    public void deleteById(String i) {

    }

    @Override
    public Test findById(String i) {
        return null;
    }
    
}
