package cr.ac.ucr.orientaucr.orientaucr.service;

import cr.ac.ucr.orientaucr.orientaucr.dao_implements.UserDAOImplements;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import java.util.LinkedList;

public class UserService {

   
        private static  final UserDAOImplements  dataUser= new UserDAOImplements();

   

    public static LinkedList<User> getAllUsers() {
        return dataUser.getAll();
    }

    public static LinkedList<User> searchUsers(String searchTerm) {
        return dataUser.getAll(searchTerm);
    }

    public static User getUserById(String userId) {
        return dataUser.findById(userId);
    }

    public static void createUser(User user) {
        dataUser.add(user);
    }

    public static void updateUser(User user) {
        dataUser.update(user);
    }

    public static void deleteUser(String userId) {
        dataUser.deleteById(userId);
    }
}