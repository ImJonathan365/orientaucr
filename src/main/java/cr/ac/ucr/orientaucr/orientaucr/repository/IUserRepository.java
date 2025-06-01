package cr.ac.ucr.orientaucr.orientaucr.repository;

import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, String> {
    
}
