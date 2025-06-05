package cr.ac.ucr.orientaucr.orientaucr.services;

import cr.ac.ucr.orientaucr.orientaucr.domain.User;

public interface IUserService extends CRUD<User> {

    User authenticateUser(String email, String password);

}
