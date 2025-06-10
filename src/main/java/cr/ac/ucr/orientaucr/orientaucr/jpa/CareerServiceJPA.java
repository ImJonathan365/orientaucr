package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.Career;
import cr.ac.ucr.orientaucr.orientaucr.repository.ICareerRepository;
import cr.ac.ucr.orientaucr.orientaucr.repository.ICharacteristicRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.ICareerService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CareerServiceJPA implements ICareerService {

    @Autowired
    private ICareerRepository careerRepo;

    @Autowired
    private ICharacteristicRepository characteristicRepo;

    @Override
    public List<Career> getAll(String search) {
        return null;
    }

    @Override
    public List<Career> getAll() {
        return careerRepo.findAll();
    }

    @Override
    @Transactional
    public void add(Career t) {
        t.setCareerId(UUID.randomUUID().toString());
        careerRepo.save(t);
    }

    @Override
    public void update(Career t) {
        Career existing = careerRepo.findById(t.getCareerId()).orElseThrow();
        existing.setCareerName(t.getCareerName());
        existing.setCareerDescription(t.getCareerDescription());
        existing.setCareerDurationYears(t.getCareerDurationYears());
        careerRepo.save(existing);
    }

    @Override
    public void deleteById(String i) {
        Career career = careerRepo.findById(i).orElseThrow();
        career.getCharacteristics().clear(); 
        careerRepo.save(career); 
        careerRepo.deleteById(i);
    }

    @Override
    public Career findById(String i) {
        return careerRepo.findById(i).get();
    }

}
