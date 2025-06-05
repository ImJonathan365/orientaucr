package cr.ac.ucr.orientaucr.orientaucr.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.LinkedList;

@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @Column(name = "permission_id", length = 36)
    private String permissionId;

    @Column(name = "permission_name", nullable = false, unique = true, length = 100)
    private String permissionName;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String permissionDescription;

    @ManyToMany(mappedBy = "permissions")
    private LinkedList<Roles> roles = new LinkedList<>();

    public Permission() {}

    public Permission(String permissionId, String permissionName, String permissionDescription) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
        this.permissionDescription = permissionDescription;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }

    public void setPermissionDescription(String permissionDescription) {
        this.permissionDescription = permissionDescription;
    }

    public LinkedList<Roles> getRoles() {
        return roles;
    }

    public void setRoles(LinkedList<Roles> roles) {
        this.roles = roles;
    }

}