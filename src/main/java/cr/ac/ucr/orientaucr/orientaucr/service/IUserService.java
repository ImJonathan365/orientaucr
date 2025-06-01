package cr.ac.ucr.orientaucr.orientaucr.service;

import cr.ac.ucr.orientaucr.orientaucr.domain.User;

public interface IUserService extends CRUD<User> {

    public User authenticateUser(String email, String password);

}
