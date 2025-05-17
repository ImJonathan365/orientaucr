package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.service.RolesService;
import java.util.Collections;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/roles")
public class RolesController {

    @GetMapping("/list")
    @ResponseBody
    public Map getListRoles() {
        return Collections.singletonMap("data", RolesService.getAllRoles());
    }

    @PostMapping("/add")
    @ResponseBody
    public Map addRoles(@RequestBody Roles rol) {
        RolesService.add(rol);
        return getListRoles();
    }
}
