package cr.ac.ucr.orientaucr.orientaucr.domain;

public class Permission {

    private String permission_id;
    private String permission_name;
    private String permission_description;

    public Permission() {}

    public Permission(String permission_id, String permission_name, String permission_description) {
        this.permission_id = permission_id;
        this.permission_name = permission_name;
        this.permission_description = permission_description;
    }

    public String getPermission_id() {
        return permission_id;
    }

    public void setPermission_id(String permission_id) {
        this.permission_id = permission_id;
    }

    public String getPermission_name() {
        return permission_name;
    }

    public void setPermission_name(String permission_name) {
        this.permission_name = permission_name;
    }

    public String getPermission_description() {
        return permission_description;
    }

    public void setPermission_description(String permission_description) {
        this.permission_description = permission_description;
    }

}
