package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import cr.ac.ucr.orientaucr.orientaucr.repository.IUserRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.IUserService;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
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
        List<User> result = new ArrayList<>();
        for (User user : users) {
            if (!user.getUserId().equals(id)) {
                User userCopy = new User();
                userCopy.setUserId(user.getUserId());
                userCopy.setUserName(user.getUserName());
                userCopy.setUserLastname(user.getUserLastname());
                userCopy.setUserEmail(user.getUserEmail());
                userCopy.setUserBirthdate(user.getUserBirthdate());
                userCopy.setUserPassword("");
                userCopy.setUserDiversifiedAverage(user.getUserDiversifiedAverage());
                userCopy.setUserAllowEmailNotification(user.isUserAllowEmailNotification());
                userCopy.setUserProfilePicture(user.getUserProfilePicture());
                userCopy.setJwtToken("");
                userCopy.setUserRoles(user.getUserRoles());
                result.add(userCopy);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public List<User> getAllExcept(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo o vacío");
        }
        List<User> users = repo.findAllByOrderByUserNameAsc();
        List<User> result = new ArrayList<>();
        for (User user : users) {
            if (!user.getUserId().equals(userId)) {
                User userCopy = new User();
                userCopy.setUserId(user.getUserId());
                userCopy.setUserName(user.getUserName());
                userCopy.setUserLastname(user.getUserLastname());
                userCopy.setUserEmail(user.getUserEmail());
                userCopy.setUserBirthdate(user.getUserBirthdate());
                userCopy.setUserPassword("");
                userCopy.setUserDiversifiedAverage(user.getUserDiversifiedAverage());
                userCopy.setUserAllowEmailNotification(user.isUserAllowEmailNotification());
                userCopy.setUserProfilePicture(user.getUserProfilePicture());
                userCopy.setJwtToken("");
                userCopy.setUserRoles(user.getUserRoles());
                result.add(userCopy);
            }
        }
        return result;
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
        repo.flush();
        repo.assignDefaultRoleToUser(user.getUserId());
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

        String existingPassword = getUserPasswordById(user.getUserId());

        if (user.getUserPassword() != null && !user.getUserPassword().trim().isEmpty()
                && !passwordEncoder.matches(user.getUserPassword(), existingPassword)) {
            user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        } else {
            user.setUserPassword(existingPassword);
        }

        repo.updateUser(
                user.getUserId(),
                user.getUserName(),
                user.getUserLastname(),
                user.getUserEmail(),
                user.getUserBirthdate(),
                user.getUserPassword(),
                user.getUserDiversifiedAverage(),
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
        repo.flush();
        repo.assignDefaultRoleToUser(user.getUserId());
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
        User result = new User();
        if (user != null) {
            result.setUserId(user.getUserId());
            result.setUserName(user.getUserName());
            result.setUserLastname(user.getUserLastname());
            result.setUserEmail(user.getUserEmail());
            result.setUserBirthdate(user.getUserBirthdate());
            result.setUserPassword("");
            result.setUserDiversifiedAverage(user.getUserDiversifiedAverage());
            result.setUserAllowEmailNotification(user.isUserAllowEmailNotification());
            result.setUserProfilePicture(user.getUserProfilePicture());
            result.setJwtToken("");
            result.setUserRoles(user.getUserRoles());
        }
        return result;
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

    @Override
    @Transactional
    public String getUserPasswordById(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo o vacío");
        }
        Optional<User> userOptional = repo.findById(userId);
        return userOptional.map(User::getUserPassword).orElse(null);
    }
    
    @Override
    @Transactional
    public List<Roles> getRolesByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico no puede ser nulo o vacío");
        }
        List<Roles> rolesList = new ArrayList<>();
        Map<String, Roles> roleMap = new HashMap<>();
        List<Object[]> results = repo.getRolesAndPermissionsByEmail(email);

        for (Object[] result : results) {
            String rolId = (String) result[0];
            String rolName = (String) result[1];
            String permissionId = (String) result[2];
            String permissionName = (String) result[3];
            String permissionDescription = (String) result[4];

            Roles role = roleMap.getOrDefault(rolId, new Roles(rolId, rolName));
            if (!roleMap.containsKey(rolId)) {
                roleMap.put(rolId, role);
                rolesList.add(role);
            }

            if (permissionId != null) {
                Permission permission = new Permission(permissionId, permissionName, permissionDescription);
                role.getPermissions().add(permission);
            }
        }
        return rolesList;
    }

}
