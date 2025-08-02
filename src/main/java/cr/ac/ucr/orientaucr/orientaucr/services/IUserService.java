package cr.ac.ucr.orientaucr.orientaucr.services;

import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import java.util.List;
import java.util.Optional;

public interface IUserService extends CRUD<User> {
    User authenticateUser(String email, String password);
    List<User> searchAllExcept(String search, String id);
    List<User> getAllExcept(String id);
    Optional<User> findByEmail(String email);
    void updateUserToken(String id, String token);
    String getUserPasswordById(String userId);
    List<Roles> getRolesByEmail(String email);
    void verifyUserEmail(String id);
}
