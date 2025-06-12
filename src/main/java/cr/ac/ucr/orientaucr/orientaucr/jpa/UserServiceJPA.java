package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import cr.ac.ucr.orientaucr.orientaucr.repository.IUserRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.IUserService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceJPA implements IUserService {

    @Autowired
    private IUserRepository repo;

    @Override
    @Transactional
    public User authenticateUser(String email, String password) {
        return repo.findByUserEmailAndUserPassword(email, password);
    }

    @Override
    @Transactional
    public List<User> getAll(String search) {
        return null;
    }

    @Override
    @Transactional
    public List<User> getAll() {
        return null;
    }
    
    @Override
    @Transactional
    public List<User> searchAllExcept(String search, String id) {
        return repo.searchUsers(search, id);
    }
    
    @Override
    @Transactional
    public List<User> getAllExcept(String userId) {
        return repo.getAllUsersExcept(userId);
    }
    
    @Override
    @Transactional
    public void add(User user) {
        user.setUserId(UUID.randomUUID().toString());
        repo.save(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        repo.updateUser(
                user.getUserId(),
                user.getUserName(),
                user.getUserLastname(),
                user.getUserEmail(),
                user.getUserBirthdate(),
                user.getUserPassword(),
                user.getUserAdmissionAverage(),
                user.isUserAllowEmailNotification(),
                user.getUserProfilePicture()
        );

        if (user.getUserRoles() != null) {
            repo.deleteRolesFromUser(user.getUserId());

            for (Roles role : user.getUserRoles()) {
                repo.addRoleToUser(user.getUserId(), role.getRolId());
            }
        }
    }

    @Override
    public void deleteById(String id) {
        repo.deleteRolesFromUser(id);
        repo.deleteUser(id);
    }

    @Override
    @Transactional
    public User findById(String id) {
        return repo.findById(id).orElse(null);
    }

}
