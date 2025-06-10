
package cr.ac.ucr.orientaucr.orientaucr.repository;

import cr.ac.ucr.orientaucr.orientaucr.domain.Curricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICurriculaRepository extends JpaRepository<Curricula, String>{
    
}
