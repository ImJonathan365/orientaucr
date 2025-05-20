package cr.ac.ucr.orientaucr.orientaucr.domain;

import java.util.LinkedList;

public class UpdateUserRolePermissionDTO {
    
    private String userId;
    private String roleId;
    private LinkedList<String> permissions;

    public UpdateUserRolePermissionDTO(String userId, String roleId, LinkedList<String> permissions) {
        this.userId = userId;
        this.roleId = roleId;
        this.permissions = permissions;
    }

    public UpdateUserRolePermissionDTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public LinkedList<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(LinkedList<String> permissions) {
        this.permissions = permissions;
    }
    
}