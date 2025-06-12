package cr.ac.ucr.orientaucr.orientaucr.services;

import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import java.util.List;

public interface IUserService extends CRUD<User> {

    User authenticateUser(String email, String password);
    
    List<User> searchAllExcept(String search, String id);
    
    List<User> getAllExcept(String id);
    
}
