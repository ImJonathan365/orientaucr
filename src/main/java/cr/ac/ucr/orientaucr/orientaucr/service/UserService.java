package cr.ac.ucr.orientaucr.orientaucr.service;

import cr.ac.ucr.orientaucr.orientaucr.dao_implements.UserDAOImplements;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import java.util.LinkedList;

public class UserService {

    private final UserDAOImplements userDao;

    public UserService() {
        this.userDao = new UserDAOImplements();
    }

    public LinkedList<User> getAllUsers() {
        return userDao.getAll();
    }

    public LinkedList<User> searchUsers(String searchTerm) {
        return userDao.getAll(searchTerm);
    }

    public User getUserById(String userId) {
        return userDao.findById(userId);
    }

    public void createUser(User user) {
        userDao.add(user);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void deleteUser(String userId) {
        userDao.deleteById(userId);
    }
}