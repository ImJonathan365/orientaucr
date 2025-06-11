package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.Characteristic;
import cr.ac.ucr.orientaucr.orientaucr.repository.ICareerRepository;
import cr.ac.ucr.orientaucr.orientaucr.repository.ICharacteristicRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.ICharacteristicsService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector.Characteristics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacteristicServiceJPA implements ICharacteristicsService {

    @Autowired
    private ICareerRepository repo;
    
    @Autowired
    private ICharacteristicRepository characteristicRepo;

    @Override
    public List<Characteristic> getAll(String search) {
        return null;
    }

    @Override
    public List<Characteristic> getAll() {
        return characteristicRepo.findAll();
    }

    @Override
    public void add(Characteristic t) {
        t.setCharacteristicsId(UUID.randomUUID().toString());
        characteristicRepo.save(t);
    }

    @Override
    public void update(Characteristic t) {
        Characteristic existing = characteristicRepo.findById(t.getCharacteristicsId()).orElseThrow();
        existing.setCharacteristicsName(t.getCharacteristicsName());
        existing.setCharacteristicsDescription(t.getCharacteristicsDescription());
        characteristicRepo.save(existing);
    }

    @Override
    public void deleteById(String i) {
        characteristicRepo.deleteById(i);
    }

    @Override
    public Characteristic findById(String i) {
        return characteristicRepo.findById(i).get();

    }

}
