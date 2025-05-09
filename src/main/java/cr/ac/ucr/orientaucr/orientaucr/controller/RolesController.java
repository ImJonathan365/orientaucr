/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
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

/**
 *
 * @author carlo
 */
@Controller
 @RequestMapping("Roles")
public class RolesController {

    @GetMapping("/listRoles")
   @ResponseBody
    public Map getListRoles(){
    return Collections.singletonMap("data",RolesService.getAllRoles());
    }
     @PostMapping("/add")
    @ResponseBody
    public Map addRoles(@RequestBody Roles rol){
       RolesService.add(rol);
    return getListRoles();
    }
}
