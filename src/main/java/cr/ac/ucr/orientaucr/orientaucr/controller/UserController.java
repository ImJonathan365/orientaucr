package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import cr.ac.ucr.orientaucr.orientaucr.services.IUserService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserService service;
    
    
    private final List<String> ALLOWED_IMAGE_TYPES = List.of("image/jpeg", "image/jpg", "image/png");

    @RequestMapping("/list")
    @ResponseBody
    public ResponseEntity<LinkedList<User>> getAllUsers() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/users/search")
    public ResponseEntity<LinkedList<User>> searchUsers(@RequestParam("q") String search) {
        try {
            LinkedList<User> users = service.getAll(search);

            if (users.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestPart("user") User user, @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                //String imagePath = imageService.saveImage(imageFile);
                //user.setUser_profile_picture(imagePath);
            }

            service.add(user);
            return ResponseEntity.ok("Usuario agregado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar usuario: " + e.getMessage());
        }
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<String> updateUser(@RequestPart("user") User updatedUser, @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            if (updatedUser.getUserId()== null || updatedUser.getUserId().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("ID de usuario requerido.");
            }
            User existingUser = service.findById(updatedUser.getUserId());
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }

            if (imageFile != null && !imageFile.isEmpty()) {
                //String imagePath = imageService.saveImage(imageFile);
                //updatedUser.setUser_profile_picture(imagePath);
            } else {
                updatedUser.setUserProfilePicture(existingUser.getUserProfilePicture());
            }

            service.update(updatedUser);
            return ResponseEntity.ok("Usuario actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar usuario: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<String> deleteUser(@PathVariable("user_id") String userId) {
        try {
            if (userId == null || userId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("ID de usuario requerido.");
            }

            service.deleteById(userId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuario eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el usuario: " + e.getMessage());
        }
    }

    @GetMapping("/search/{user_id}")
    public ResponseEntity<User> getUserById(@PathVariable("user_id") String userId) {
        try {
            if (userId == null || userId.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            User user = service.findById(userId);

            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User credentials) {
        try {
            if (credentials.getUserEmail() == null || credentials.getUserPassword() == null) {
                return ResponseEntity.badRequest().build();
            }
            
            User user = service.authenticateUser(
                    credentials.getUserEmail(),
                    credentials.getUserPassword()
            );

            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
