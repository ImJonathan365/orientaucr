package cr.ac.ucr.orientaucr.orientaucr.repository;

import cr.ac.ucr.orientaucr.orientaucr.domain.Subcampus;
import cr.ac.ucr.orientaucr.orientaucr.domain.SubcampusId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcampusRepository extends JpaRepository<Subcampus,String> {

    // Buscar todos los subcampus de un campus específico
    List<Subcampus> findByCampusCampusId(String campusId);
}
