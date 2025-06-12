package cr.ac.ucr.orientaucr.orientaucr.repository;

import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
=======
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
>>>>>>> Luis

@Repository
public interface IUserRepository extends JpaRepository<User, String> {
<<<<<<< HEAD

    User findByUserEmailAndUserPassword(String email, String password);

    @Procedure(procedureName = "sp_authenticate_user")
    User authenticateUser(@Param("p_email") String email, @Param("p_password") String password);

    @Procedure(procedureName = "sp_search_users")
    List<User> searchUsers(@Param("p_search") String search, @Param("p_user_id") String userId);

    @Procedure(procedureName = "sp_get_all_users")
    List<User> getAllUsers();

    @Procedure(procedureName = "sp_get_all_users_except")
    List<User> getAllUsersExcept(@Param("p_user_id") String userId);

    @Procedure(procedureName = "sp_add_user")
    void addUser(
            @Param("p_user_id") String userId,
            @Param("p_name") String name,
            @Param("p_lastname") String lastname,
            @Param("p_email") String email,
            @Param("p_birthdate") Date birthdate,
            @Param("p_password") String password,
            @Param("p_average") BigDecimal average,
            @Param("p_allow_email") Boolean allowEmail,
            @Param("p_profile_picture") String profilePicture
    );

    @Procedure(procedureName = "sp_update_user")
    void updateUser(
            @Param("p_user_id") String userId,
            @Param("p_name") String name,
            @Param("p_lastname") String lastname,
            @Param("p_email") String email,
            @Param("p_birthdate") Date birthdate,
            @Param("p_password") String password,
            @Param("p_average") BigDecimal average,
            @Param("p_allow_email") Boolean allowEmail,
            @Param("p_profile_picture") String profilePicture
    );

    @Procedure(procedureName = "sp_delete_user")
    void deleteUser(@Param("p_id") String userId);

    @Procedure(procedureName = "sp_delete_roles_from_user")
    void deleteRolesFromUser(@Param("p_user_id") String userId);

    @Procedure(procedureName = "sp_add_role_to_user")
    void addRoleToUser(@Param("p_user_id") String userId, @Param("p_rol_id") String roleId);

    @Procedure(procedureName = "sp_get_user_by_id")
    User findUserById(@Param("p_id") String userId);

    @Procedure(procedureName = "sp_get_roles_by_user_id")
    List<Roles> getRolesByUserId(@Param("p_user_id") String userId);

    @Procedure(procedureName = "sp_get_permissions_by_role_id")
    List<Permission> getPermissionsByRoleId(@Param("p_rol_id") String roleId);

}
=======
    @Query(
        value = "SELECT u.* FROM users u " +
                "JOIN user_interested_event ue ON u.user_id = ue.user_id " +
                "WHERE ue.event_id = :eventId AND u.user_allow_email_notification = true",
        nativeQuery = true
    )
    List<User> findInterestedUsersWithNotificationsEnabled(@Param("eventId") String eventId);
}
>>>>>>> Luis
