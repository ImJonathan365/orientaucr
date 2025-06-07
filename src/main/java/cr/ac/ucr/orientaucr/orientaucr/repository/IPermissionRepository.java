package cr.ac.ucr.orientaucr.orientaucr.repository;

import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface IPermissionRepository extends JpaRepository<Permission, String> {
    
    @Query(value = "CALL sp_get_all_permissions()", nativeQuery = true)
    List<Permission> getAllPermissions();
    
    Optional<Permission> findByPermissionName(String permissionName);
}