package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.Characteristic;
import cr.ac.ucr.orientaucr.orientaucr.repository.ICareerRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.ICharacteristicsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacteristicServiceJPA implements ICharacteristicsService {

    @Autowired
    private ICareerRepository repo;

    @Override
    public List<Characteristic> getAll(String search) {
        return null;
    }

    @Override
    public List<Characteristic> getAll() {
        return null;
    }

    @Override
    public void add(Characteristic t) {

    }

    @Override
    public void update(Characteristic t) {

    }

    @Override
    public void deleteById(String i) {

    }

    @Override
    public Characteristic findById(String i) {
        return null;

    }

}
