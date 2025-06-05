package cr.ac.ucr.orientaucr.orientaucr.repository;

import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import java.util.LinkedList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, String> {

    Optional<User> findByUserEmail(String email);

    LinkedList<User> findByUserNameContainingIgnoreCaseOrUserLastnameContainingIgnoreCaseOrUserEmailContainingIgnoreCase(
            String userName, String userLastname, String userEmail
    );
    
}
