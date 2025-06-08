package cr.ac.ucr.orientaucr.orientaucr.services.impl;

import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("sp_authenticate_user", User.class)
                .registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_password", String.class, ParameterMode.IN)
                .setParameter("p_email", email)
                .setParameter("p_password", "dummy");

            User user = (User) query.getSingleResult();

            if (user == null) {
                throw new UsernameNotFoundException("Usuario no encontrado con el email: " + email);
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

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (Roles role : user.getUserRoles()) {
                for (Permission perm : role.getPermissions()) {
                    authorities.add(new SimpleGrantedAuthority(perm.getPermissionName()));
                }
            }

            return new org.springframework.security.core.userdetails.User(
                user.getUserEmail(),
                user.getUserPassword(),
                authorities
            );

        } catch (NoResultException e) {
            throw new UsernameNotFoundException("Usuario no encontrado con el email: " + email);
        } catch (Exception e) {
            throw new RuntimeException("Error cargando detalles del usuario: " + e.getMessage(), e);
        }
    }
}
