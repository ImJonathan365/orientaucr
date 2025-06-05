package cr.ac.ucr.orientaucr.orientaucr.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.LinkedList;

@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @Column(name = "rol_id", length = 36)
    private String rolId;

    @Column(name = "rol_name", nullable = false, unique = true, length = 50)
    private String rolName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rol_permission",
        joinColumns = @JoinColumn(name = "rol_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private LinkedList<Permission> permissions = new LinkedList<>();

    @ManyToMany(mappedBy = "userRoles")
    private LinkedList<User> users = new LinkedList<>();

    public Roles() {}

    public Roles(String rolId, String rolName) {
        this.rolId = rolId;
        this.rolName = rolName;
    }

    public String getRolId() {
        return rolId;
    }

    public void setRolId(String rolId) {
        this.rolId = rolId;
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }

    public LinkedList<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(LinkedList<Permission> permissions) {
        this.permissions = permissions;
    }

    public LinkedList<User> getUsers() {
        return users;
    }

    public void setUsers(LinkedList<User> users) {
        this.users = users;
    }
    
}