package cr.ac.ucr.orientaucr.orientaucr.repository;

import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {

    Optional<User> findByUserEmail(String email);

    @Procedure(procedureName = "sp_assign_default_role_to_user")
    void assignDefaultRoleToUser(@Param("p_user_id") String userId);
    
    @Procedure(procedureName = "sp_authenticate_user")
    User authenticateUser(@Param("p_email") String email, @Param("p_password") String password);

    @Procedure(procedureName = "sp_search_all_users_except")
    List<User> searchAllUsersExcept(@Param("p_search") String search, @Param("p_user_id") String userId);

    List<User> findByUserNameContainingOrUserLastnameContainingOrUserEmailContainingOrderByUserNameAsc(String userName, String userLastname, String userEmail);
   
    @Procedure(procedureName = "sp_get_all_users_except")
    List<User> getAllUsersExcept(@Param("p_user_id") String userId);

    List<User> findAllByOrderByUserNameAsc();

    @Procedure(procedureName = "sp_add_user")
    void addUser(
            @Param("p_user_id") String userId,
            @Param("p_name") String name,
            @Param("p_lastname") String lastname,
            @Param("p_email") String email,
            @Param("p_birthdate") LocalDate birthdate,
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
            @Param("p_birthdate") LocalDate birthdate,
            @Param("p_password") String password,
            @Param("p_average") BigDecimal average,
            @Param("p_allow_email") Boolean allowEmail,
            @Param("p_profile_picture") String profilePicture,
            @Param("p_token") String token
    );

    @Procedure(procedureName = "sp_update_user_token")
    void updateUserToken(@Param("p_user_id") String userId, @Param("p_jwt_token") String userToken);

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

    @Procedure(procedureName = "sp_get_roles_and_permissions_by_email")
    List<Object[]> getRolesAndPermissionsByEmail(@Param("p_email") String email);

    @Procedure(procedureName = "sp_find_interested_users_with_notifications")
    List<User> findInterestedUsersWithNotificationsEnabled(@Param("p_event_id") String eventId);

    @Procedure(procedureName = "sp_verify_user_email")
    void verifyUserEmail(@Param("p_user_id") String userId);
}
