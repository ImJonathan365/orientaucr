package cr.ac.ucr.orientaucr.orientaucr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

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
    @JsonIgnoreProperties("roles")
    private List<Permission> permissions = new ArrayList<>();
    
    @ManyToMany(mappedBy = "userRoles")
    @JsonIgnore
    private List<User> users = new ArrayList<>();
    
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

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
}