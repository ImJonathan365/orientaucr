package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import cr.ac.ucr.orientaucr.orientaucr.repository.IUserRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.IUserService;
import java.util.LinkedList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceJPA implements IUserService {

    @Autowired
    private IUserRepository repo;
    
    @Override
    public User authenticateUser(String email, String password) {
        Optional<User> optionalUser = repo.findByUserEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user.getUserPassword().equals(password) ? user : null;
        }
        return null;
    }

    @Override
    public LinkedList<User> getAll(String search) {
        if (search == null || search.isEmpty()) {
            return new LinkedList<>(repo.findAll());
        }
        return repo.findByUserNameContainingIgnoreCaseOrUserLastnameContainingIgnoreCaseOrUserEmailContainingIgnoreCase(
                search, search, search
        );
    }

    @Override
    public LinkedList<User> getAll() {
        return new LinkedList<>(repo.findAll());
    }

    @Override
    public void add(User user) {
        if (user != null) {
            repo.save(user);
        }
    }

    @Override
    public void update(User user) {
        if (user != null && repo.existsById(user.getUserId())) {
            repo.save(user);
        }
    }

    @Override
    public void deleteById(String id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }

    @Override
    public User findById(String id) {
        return repo.findById(id).orElse(null);
    }
    
}
