package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.service.PermissionsToUsersService;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/roles")
public class RolesController {

    @GetMapping("/list")
    @ResponseBody
    public Map<String, Object> getListRoles(@RequestParam(required = false) String rol_id) {
        LinkedList<?> data = PermissionsToUsersService.getAllRolesOrPermissions(rol_id);
        return Collections.singletonMap("data", data);
    }

    
    @GetMapping("/permissions/user")
    @ResponseBody
    public Map<String, Object> getPermissionsOfUser(@RequestParam String user_id) {
        LinkedList<?> data = PermissionsToUsersService.getAllPermissionOfUser(user_id);
        return Collections.singletonMap("data", data);
    }

    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> addPermissions(@RequestBody Map<String, String> payload) {
        String user_id = payload.get("user_id");
        String permission_id = payload.get("permission_id");

       
        if (user_id == null || permission_id == null || user_id.isEmpty() || permission_id.isEmpty()) {
            return Collections.singletonMap("error", "user_id y permission_id son obligatorios");
        }

        PermissionsToUsersService.add(user_id, permission_id);

       
        LinkedList<?> updatedPermissions = PermissionsToUsersService.getAllPermissionOfUser(user_id);
        return Collections.singletonMap("data", updatedPermissions);
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> deletePermissions(@RequestBody Map<String, String> payload) {
        String user_id = payload.get("user_id");
        String permission_id = payload.get("permission_id");

        if (user_id == null || permission_id == null || user_id.isEmpty() || permission_id.isEmpty()) {
            return Collections.singletonMap("error", "user_id y permission_id son obligatorios");
        }

        PermissionsToUsersService.delete(user_id, permission_id);

        LinkedList<?> updatedPermissions = PermissionsToUsersService.getAllPermissionOfUser(user_id);
        return Collections.singletonMap("data", updatedPermissions);
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateRole(@RequestBody Map<String, String> payload) {
        String user_id = payload.get("user_id");
        String rol_id = payload.get("rol_id");

        if (user_id == null || rol_id == null || user_id.isEmpty() || rol_id.isEmpty()) {
            return Collections.singletonMap("error", "user_id y rol_id son obligatorios");
        }

        PermissionsToUsersService.update(user_id, rol_id);

        LinkedList<?> updatedRoles = PermissionsToUsersService.getAllRolesOrPermissions(rol_id);
        return Collections.singletonMap("data", updatedRoles);
    }
     @GetMapping("/FindById")
@ResponseBody
public Map<String, Object> getRolNameByUserId(@RequestParam String user_id) {
    if (user_id == null || user_id.isEmpty()) {
        return Collections.singletonMap("error", "El parámetro 'user_id' es obligatorio");
    }

    String rolName = PermissionsToUsersService.findById(user_id);

    if (rolName == null) {
        return Collections.singletonMap("error", "No se encontró un rol para el user_id especificado");
    }

    return Collections.singletonMap("rol_name", rolName);
}

}
