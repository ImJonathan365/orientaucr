package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.Campus;
import cr.ac.ucr.orientaucr.orientaucr.services.lCampus;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cr.ac.ucr.orientaucr.orientaucr.repository.ICampusRepository;

@Service
public class CampusServiceJPA implements lCampus {

    @Autowired
    private ICampusRepository repo;

    @Override
    public List<Campus> getAll(String search) {
        if (search == null || search.isBlank()) {
            return repo.findAll();
        } else {
            // Aquí puedes implementar búsqueda por nombre o ubicación (depende si agregas métodos en repo)
            // Por ejemplo, si agregas findByCampusNameContainingIgnoreCase(String name)
            return repo.findAll().stream()
                .filter(c -> c.getCampusName().toLowerCase().contains(search.toLowerCase()))
                .toList();
        }
    }

    @Override
    public List<Campus> getAll() {
        return repo.findAll();
    }

    @Override
    public void add(Campus t) {
        repo.save(t);
    }

    @Override
    public void update(Campus t) {
        repo.save(t);
    }

    @Override
    public void deleteById(String id) {
        repo.deleteById(id);
    }

    @Override
    public Campus findById(String id) {
        Optional<Campus> optional = repo.findById(id);
        return optional.orElse(null);
    }
}
