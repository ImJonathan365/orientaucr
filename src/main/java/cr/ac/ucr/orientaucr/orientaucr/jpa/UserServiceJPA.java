package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import cr.ac.ucr.orientaucr.orientaucr.services.IUserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceJPA implements IUserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User authenticateUser(String email, String password) {
        try {
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("sp_authenticate_user", User.class)
                    .registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_password", String.class, ParameterMode.IN)
                    .setParameter("p_email", email)
                    .setParameter("p_password", password);

            User user = (User) query.getSingleResult();
            populateUserRolesAndPermissions(user);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<User> getAll(String search) {
        try {
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("sp_search_users", User.class)
                    .registerStoredProcedureParameter("p_search", String.class, ParameterMode.IN)
                    .setParameter("p_search", search);

            List<User> users = query.getResultList();
            users.forEach(this::populateUserRolesAndPermissions);
            return new ArrayList<>(users);
        } catch (Exception e) {
            return new ArrayList<>() {};
        }
    }

    @Override
    public List<User> getAll() {
        try {
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("sp_get_all_users", User.class);

            List<User> users = query.getResultList();
            users.forEach(this::populateUserRolesAndPermissions);
            return new ArrayList<>(users);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void add(User user) {
        if (user == null || user.getUserEmail() == null || user.getUserPassword() == null) {
            throw new IllegalArgumentException("Usuario inválido.");
        }

        try {
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("sp_add_user")
                    .registerStoredProcedureParameter("p_user_id", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_name", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_lastname", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_birthdate", Date.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_password", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_average", BigDecimal.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_allow_email", Boolean.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_profile_picture", String.class, ParameterMode.IN)
                    .setParameter("p_user_id", user.getUserId())
                    .setParameter("p_name", user.getUserName())
                    .setParameter("p_lastname", user.getUserLastname())
                    .setParameter("p_email", user.getUserEmail())
                    .setParameter("p_birthdate", user.getUserBirthdate())
                    .setParameter("p_password", user.getUserPassword())
                    .setParameter("p_average", user.getUserAdmissionAverage())
                    .setParameter("p_allow_email", user.isUserAllowEmailNotification())
                    .setParameter("p_profile_picture", user.getUserProfilePicture());

            query.execute();

            if (user.getUserRoles() != null) {
                for (Roles role : user.getUserRoles()) {
                    StoredProcedureQuery roleQuery = entityManager
                            .createStoredProcedureQuery("sp_add_role_to_user")
                            .registerStoredProcedureParameter("p_user_id", String.class, ParameterMode.IN)
                            .registerStoredProcedureParameter("p_rol_id", String.class, ParameterMode.IN)
                            .setParameter("p_user_id", user.getUserId())
                            .setParameter("p_rol_id", role.getRolId());

                    roleQuery.execute();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al agregar usuario y sus roles: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(User user) {
        if (user == null || user.getUserId() == null || user.getUserEmail() == null) {
            throw new IllegalArgumentException("Usuario inválido.");
        }

        try {
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("sp_update_user")
                    .registerStoredProcedureParameter("p_user_id", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_name", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_lastname", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_birthdate", Date.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_password", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_average", BigDecimal.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_allow_email", Boolean.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_profile_picture", String.class, ParameterMode.IN)
                    .setParameter("p_user_id", user.getUserId())
                    .setParameter("p_name", user.getUserName())
                    .setParameter("p_lastname", user.getUserLastname())
                    .setParameter("p_email", user.getUserEmail())
                    .setParameter("p_birthdate", user.getUserBirthdate())
                    .setParameter("p_password", user.getUserPassword())
                    .setParameter("p_average", user.getUserAdmissionAverage())
                    .setParameter("p_allow_email", user.isUserAllowEmailNotification())
                    .setParameter("p_profile_picture", user.getUserProfilePicture());

            query.execute();

            StoredProcedureQuery delRoles = entityManager
                    .createStoredProcedureQuery("sp_delete_roles_from_user")
                    .registerStoredProcedureParameter("p_user_id", String.class, ParameterMode.IN)
                    .setParameter("p_user_id", user.getUserId());

            delRoles.execute();

            if (user.getUserRoles() != null) {
                for (Roles role : user.getUserRoles()) {
                    StoredProcedureQuery roleQuery = entityManager
                            .createStoredProcedureQuery("sp_add_role_to_user")
                            .registerStoredProcedureParameter("p_user_id", String.class, ParameterMode.IN)
                            .registerStoredProcedureParameter("p_rol_id", String.class, ParameterMode.IN)
                            .setParameter("p_user_id", user.getUserId())
                            .setParameter("p_rol_id", role.getRolId());

                    roleQuery.execute();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar usuario y sus roles: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            StoredProcedureQuery delRoles = entityManager
                    .createStoredProcedureQuery("sp_delete_roles_from_user")
                    .registerStoredProcedureParameter("p_user_id", String.class, ParameterMode.IN)
                    .setParameter("p_user_id", id);

            delRoles.execute();

            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("sp_delete_user")
                    .registerStoredProcedureParameter("p_id", String.class, ParameterMode.IN)
                    .setParameter("p_id", id);

            query.execute();

        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar usuario y sus roles: " + e.getMessage(), e);
        }
    }

    @Override
    public User findById(String id) {
        try {
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("sp_get_user_by_id", User.class)
                    .registerStoredProcedureParameter("p_id", String.class, ParameterMode.IN)
                    .setParameter("p_id", id);

            User user = (User) query.getSingleResult();
            populateUserRolesAndPermissions(user);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    private void populateUserRolesAndPermissions(User user) {
        if (user == null) {
            return;
        }

        StoredProcedureQuery roleQuery = entityManager
                .createStoredProcedureQuery("sp_get_roles_by_user_id", Roles.class)
                .registerStoredProcedureParameter("p_user_id", String.class, ParameterMode.IN)
                .setParameter("p_user_id", user.getUserId());

        List<Roles> roles = roleQuery.getResultList();

        for (Roles role : roles) {
            StoredProcedureQuery permQuery = entityManager
                    .createStoredProcedureQuery("sp_get_permissions_by_role_id", Permission.class)
                    .registerStoredProcedureParameter("p_rol_id", String.class, ParameterMode.IN)
                    .setParameter("p_rol_id", role.getRolId());

            List<Permission> permissions = permQuery.getResultList();
            role.setPermissions(permissions);
        }

        user.setUserRoles(new ArrayList<>(roles));
    }

}
