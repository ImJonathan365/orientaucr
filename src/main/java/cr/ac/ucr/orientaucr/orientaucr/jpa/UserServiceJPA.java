package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import cr.ac.ucr.orientaucr.orientaucr.repository.IUserRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.IUserService;
import java.sql.Types;
import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceJPA implements IUserService {

    @Autowired
    private IUserRepository repo;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
        
    @Override
    public User authenticateUser(String email, String password) {
        String procedureCall = "CALL sp_authenticate_user(?, ?)";
        Object[] parameters = {email, password};
        int[] parameterTypes = {Types.VARCHAR, Types.VARCHAR};
        
        User user = jdbcTemplate.queryForObject(procedureCall, parameters, parameterTypes, (rs, rowNum) -> {
            User u = new User();
            u.setUserId(rs.getString("user_id"));
            u.setUserEmail(rs.getString("user_email"));
            return u;
        });

        return user;
    }

    @Override
    public LinkedList<User> getAll(String search) {
        if (search == null || search.isEmpty()) {
            return new LinkedList<>(repo.findAll());
        }
        return repo.findByUserNameContainingIgnoreCaseOrUserLastnameContainingIgnoreCaseOrUserEmailContainingIgnoreCase(
                search, search, search
        );
    }

    @Override
    public LinkedList<User> getAll() {
        return new LinkedList<>(repo.findAll());
    }

    @Override
    public void add(User user) {
        if (user != null) {
            repo.save(user);
        }
    }

    @Override
    public void update(User user) {
        if (user != null && repo.existsById(user.getUserId())) {
            repo.save(user);
        }
    }

    @Override
    public void deleteById(String id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }

    @Override
    public User findById(String id) {
        return repo.findById(id).orElse(null);
    }
    
}
