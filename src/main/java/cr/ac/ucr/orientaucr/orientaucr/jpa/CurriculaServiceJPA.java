
package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.Curricula;
import cr.ac.ucr.orientaucr.orientaucr.repository.ICurriculaRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.ICurriculaService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurriculaServiceJPA implements ICurriculaService{
    
    @Autowired
    private ICurriculaRepository curriculaRepo;

    @Override
    public List<Curricula> getAll(String search) {
        return null;
    }

    @Override
    public List<Curricula> getAll() {
        return curriculaRepo.findAll();
    }

    @Override
    public void add(Curricula t) {
        t.setCurriculaId(UUID.randomUUID().toString());
        curriculaRepo.save(t);
    }

    @Override
    public void update(Curricula t) {
        Curricula existing = curriculaRepo.findById(t.getCurriculaId()).orElseThrow();
        existing.setCareer(t.getCareer());
        curriculaRepo.save(existing);
    }

    @Override
    public void deleteById(String i) {
        curriculaRepo.deleteById(i);
    }

    @Override
    public Curricula findById(String i) {
        return curriculaRepo.findById(i).get();
    }
    
}
