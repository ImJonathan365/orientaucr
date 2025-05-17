
package cr.ac.ucr.orientaucr.orientaucr.controller;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import cr.ac.ucr.orientaucr.orientaucr.service.UserService;
import java.util.Collections;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
 @RequestMapping("User")
public class UserController {

    @GetMapping("/listUser")
   @ResponseBody
    public Map getListUser(){
    return Collections.singletonMap("data",UserService.getAllUsers());
    }
     @PostMapping("/add")
    @ResponseBody
    public Map addUser(@RequestBody User user){
       UserService.createUser(user);
    return getListUser();
    }
}
