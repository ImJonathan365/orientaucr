package cr.ac.ucr.orientaucr.orientaucr.service;

import cr.ac.ucr.orientaucr.orientaucr.dao_implements.UserDAOImplements;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import java.util.LinkedList;

public class UserService {

    public UserService() {}

    private UserDAOImplements dataUser = new UserDAOImplements();

    public LinkedList<User> getAllUsers() {
        return dataUser.getAll();
    }

    public LinkedList<User> searchUsers(String searchTerm) {
        return dataUser.getAll(searchTerm);
    }

    public User getUserById(String userId) {
        return dataUser.findById(userId);
    }

    public void addUser(User user) {
        dataUser.add(user);
    }

    public void updateUser(User user) {
        dataUser.update(user);
    }

    public void deleteUser(String userId) {
        dataUser.deleteById(userId);
    }

    public User findUserById(String userId) {
        return dataUser.findById(userId);
    }

    public User authenticateUser(String email, String password) {
        return dataUser.authenticateUser(email, password);
    }
    
}
