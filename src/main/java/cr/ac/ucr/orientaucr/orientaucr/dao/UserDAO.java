
package cr.ac.ucr.orientaucr.orientaucr.dao;

import cr.ac.ucr.orientaucr.orientaucr.domain.User;

public interface UserDAO extends CRUD<User>{
    
    public User authenticateUser(String email, String password);
    
}
