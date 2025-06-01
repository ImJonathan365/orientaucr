package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import cr.ac.ucr.orientaucr.orientaucr.repository.IUserRepository;
import cr.ac.ucr.orientaucr.orientaucr.service.IUserService;
import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceJPA implements IUserService {

    @Autowired
    private IUserRepository repo;
    
    @Override
    public User authenticateUser(String email, String password) {
        return null;
    }

    @Override
    public LinkedList<User> getAll(String search) {
        return null;
    }

    @Override
    public LinkedList<User> getAll() {
        return null;
    }

    @Override
    public void add(User t) {
        
    }

    @Override
    public void update(User t) {
        
    }

    @Override
    public void deleteById(String i) {
        
    }

    @Override
    public User findById(String i) {
        return null;
    }
    
}
