package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import cr.ac.ucr.orientaucr.orientaucr.service.UserService;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController // Combina @Controller + @ResponseBody
@RequestMapping("/api/user")
@CrossOrigin(
    origins = "http://localhost:3000", // URL de tu frontend React
    allowedHeaders = "*",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
    allowCredentials = "true"
)

public class UserController {

     private final String UPLOAD_DIR = "./uploads/profile-pictures/";
    
    @GetMapping("/listUser")
    @ResponseBody
    public Map getListUser() {
        return Collections.singletonMap("data", UserService.getAllUsers());
    }

   @PostMapping
public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) {
    Map<String, Object> response = new HashMap<>();
    try {
        user.setUser_role(null); 
        
        UserService.createUser(user);
        response.put("success", true);
        response.put("message", "Usuario creado exitosamente");
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        response.put("success", false);
        response.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
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

    @GetMapping("/find")
    @ResponseBody
    public User findUser(@RequestBody Map<String, String> body) {
        String id = body.get("id");
        return UserService.findById(id);
    }
    
    @PostMapping("/login")
public ResponseEntity<Map<String, Object>> loginUser(
    @RequestBody Map<String, String> credentials) {
    
    Map<String, Object> response = new HashMap<>();
    try {
        String email = credentials.get("email");
        String password = credentials.get("password");
        
        User user = UserService.authenticate(email, password);
        if (user != null) {
            response.put("success", true);
            response.put("user", user);
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Credenciales inválidas");
            return ResponseEntity.status(401).body(response);
        }
    } catch (Exception e) {
        response.put("success", false);
        response.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
 @PostMapping("/profile-image")
    public ResponseEntity<Map<String, String>> uploadProfileImage(
            @RequestParam("image") MultipartFile file) {
        
        Map<String, String> response = new HashMap<>();
        
        try {
            // Crear directorio si no existe
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Generar nombre único para el archivo
            String fileName = UUID.randomUUID().toString() + 
                "." + StringUtils.getFilenameExtension(file.getOriginalFilename());

            // Guardar archivo
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Devolver ruta relativa
            response.put("imagePath", "/uploads/profile-pictures/" + fileName);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("error", "Error al subir la imagen");
            return ResponseEntity.status(500).body(response);
        }
    }
}
