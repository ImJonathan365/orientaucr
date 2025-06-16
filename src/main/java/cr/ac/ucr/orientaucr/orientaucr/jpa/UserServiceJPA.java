package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import cr.ac.ucr.orientaucr.orientaucr.repository.IUserRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.IUserService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceJPA implements IUserService {

    @Autowired
    private IUserRepository repo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User authenticateUser(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico no puede ser nulo o vacío");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía");
        }
        Optional<User> userOptional = repo.findByUserEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getUserPassword())) {
                return user;
            }
        }
        return null;
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
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo o vacío");
        }
        if (search == null || search.trim().isEmpty()) {
            throw new IllegalArgumentException("El parámetro de búsqueda no puede ser nulo o vacío");
        }
        List<User> users = repo
                .findByUserNameContainingOrUserLastnameContainingOrUserEmailContainingOrderByUserNameAsc(search, search, search);
        if (!users.removeIf(user -> user.getUserId().equals(id))){
            throw new IllegalArgumentException("El ID del usuario no existe");
        }
        for (User user : users) {
            user.setUserPassword("");
            user.setJwtToken("");
        }
        return users;
    }

    @Override
    @Transactional
    public List<User> getAllExcept(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo o vacío");
        }
        List<User> users = repo.findAllByOrderByUserNameAsc();
        if (!users.removeIf(user -> user.getUserId().equals(userId))) {
            throw new IllegalArgumentException("El ID del usuario no existe");
        }
        for (User user : users) {
            user.setUserPassword("");
            user.setJwtToken("");
        }
        return users;
    }

    @Override
    @Transactional
    public void add(User user) {
        if (user == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        if (user.getUserEmail() == null || user.getUserEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico no puede ser nulo o vacío");
        }
        if (user.getUserPassword() == null || user.getUserPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía");
        }
        user.setUserId(UUID.randomUUID().toString());
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        repo.save(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        if (user == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        if (user.getUserId() == null || user.getUserId().trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo o vacío");
        }
        if (user.getUserEmail() == null || user.getUserEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico no puede ser nulo o vacío");
        }

        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        repo.updateUser(
                user.getUserId(),
                user.getUserName(),
                user.getUserLastname(),
                user.getUserEmail(),
                user.getUserBirthdate(),
                user.getUserPassword(),
                user.getUserAdmissionAverage(),
                user.isUserAllowEmailNotification(),
                user.getUserProfilePicture(),
                user.getJwtToken()
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
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo o vacío");
        }
        repo.deleteRolesFromUser(id);
        repo.deleteUser(id);
    }

    @Override
    @Transactional
    public User findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo o vacío");
        }
        User user = repo.findById(id).orElse(null);
        if (user != null) {
            user.setUserPassword("");
            user.setJwtToken("");
        }
        return user;
    }

    @Override
    @Transactional
    public Optional<User> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico no puede ser nulo o vacío");
        }
        return repo.findByUserEmail(email);
    }

    @Override
    public void updateUserToken(String id, String token) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo o vacío");
        }
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("El token JWT no puede ser nulo o vacío");
        }
        repo.updateUserToken(id, token);
    }

}
