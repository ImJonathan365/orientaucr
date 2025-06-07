package cr.ac.ucr.orientaucr.orientaucr.repository;

import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface IRolesRepository extends JpaRepository<Roles, String> {
    
    Optional<Roles> findByRolName(String rolName);
    
    List<Roles> findByRolNameContainingIgnoreCase(String search);
    
    @Query(value = "CALL sp_get_roles_permissions()", nativeQuery = true)
    List<Object[]> getAllRolesWithPermissions();
    
    @Query(value = "CALL sp_get_role_by_id(:rolId)", nativeQuery = true)
    List<Object[]> getRoleWithPermissionsById(@Param("rolId") String rolId);
}