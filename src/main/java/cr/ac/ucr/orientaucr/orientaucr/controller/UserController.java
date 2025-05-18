package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import cr.ac.ucr.orientaucr.orientaucr.service.UserService;
import java.util.Collections;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


<<<<<<< HEAD
@Controller
 @RequestMapping("/api/user")

=======
@RestController // Combina @Controller + @ResponseBody
@RequestMapping("/api/user")
@CrossOrigin(
    origins = "http://localhost:3000", // URL de tu frontend React
    allowedHeaders = "*",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
    allowCredentials = "true"
)
>>>>>>> main
public class UserController {

    @GetMapping("/listUser")
    @ResponseBody
    public Map getListUser() {
        return Collections.singletonMap("data", UserService.getAllUsers());
    }

    @PostMapping("/add")
    @ResponseBody
    public Map addUser(@RequestBody User user) {
        UserService.createUser(user);
        return getListUser();
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateUser(@RequestBody User user) {
        UserService.updateUser(user);
        return getListUser();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map deleteUser(@RequestBody Map<String, String> body) {
        String id = body.get("id");
        UserService.deleteUser(id);
        return getListUser();
    }

    @PostMapping("/find")
    @ResponseBody
    public User findUser(@RequestBody Map<String, String> body) {
        String id = body.get("id");
        return UserService.findById(id);
    }
}
