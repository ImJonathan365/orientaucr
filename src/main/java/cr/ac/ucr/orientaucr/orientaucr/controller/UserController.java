package cr.ac.ucr.orientaucr.orientaucr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import cr.ac.ucr.orientaucr.orientaucr.services.IUserService;
import cr.ac.ucr.orientaucr.orientaucr.utils.ImageUtils;
import java.io.File;
import java.util.List;
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

    private ImageUtils imageService = new ImageUtils();

    private final String dirUser = System.getProperty("user.dir");

    //@PreAuthorize("hasAuthority('VER USUARIOS')")
    @RequestMapping("/list")
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsers(@RequestParam("userId") String userId) {
        try {
            if (userId == null || userId.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            List<User> users = service.getAllExcept(userId);

            users.forEach(u -> System.out.println(u.getUserName()));
            
            if (users == null) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //@PreAuthorize("hasAuthority('VER USUARIOS')")
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam("search") String search, @RequestParam("userId") String userId) {
        try {
            if (userId == null || userId.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
                        
            List<User> users = service.searchAllExcept(search, userId);

            if (users.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //@PreAuthorize("hasAuthority('CREAR USUARIOS')")
    @PostMapping("/add")
    public ResponseEntity<String> addUser(
            @RequestPart("user") String userJson,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) {
        String imagePath = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(userJson, User.class);

            if (imageFile != null && !imageFile.isEmpty()) {
                imagePath = imageService.saveImage(imageFile, dirUser + File.separator + "uploads" + File.separator + "users");
                user.setUserProfilePicture(imagePath);
            }

            service.add(user);
            return ResponseEntity.ok("Usuario agregado correctamente");
        } catch (Exception e) {
            if (imagePath != null) {
                imageService.deleteImage(dirUser + File.separator + "uploads" + File.separator + "users", imagePath);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar usuario: " + e.getMessage());
        }
    }

    //@PreAuthorize("hasAuthority('MODIFICAR USUARIOS') or hasAuthority('EDITAR PERFIL')")
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(
            @RequestPart("user") String userJson,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        String oldImage = null;
        String newImagePath = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            User updatedUser = mapper.readValue(userJson, User.class);

            if (updatedUser.getUserId() == null || updatedUser.getUserId().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("ID de usuario requerido.");
            }

            User existingUser = service.findById(updatedUser.getUserId());
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }

            oldImage = existingUser.getUserProfilePicture();

            if (imageFile != null && !imageFile.isEmpty()) {
                newImagePath = imageService.saveImage(imageFile, dirUser + File.separator + "uploads" + File.separator + "users");
                updatedUser.setUserProfilePicture(newImagePath);
            } else {
                updatedUser.setUserProfilePicture(oldImage);
            }

            service.update(updatedUser);
            if (newImagePath != null && oldImage != null && !oldImage.equals(newImagePath)) {
                imageService.deleteImage(dirUser + File.separator + "uploads" + File.separator + "users", oldImage);
            }
            return ResponseEntity.ok("Usuario actualizado correctamente");

        } catch (Exception e) {
            if (newImagePath != null) {
                imageService.deleteImage(dirUser + File.separator + "uploads" + File.separator + "users", newImagePath);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar usuario: " + e.getMessage());
        }
    }

    //@PreAuthorize("hasAuthority('ELIMINAR USUARIOS')")
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

    //@PreAuthorize("hasAuthority('VER USUARIO')")
    @GetMapping("/find/{user_id}")
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

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User simpleUser) {
        try {
            if (simpleUser.getUserName() == null || simpleUser.getUserName().isBlank()
                    || simpleUser.getUserEmail() == null || simpleUser.getUserEmail().isBlank()
                    || simpleUser.getUserPassword() == null || simpleUser.getUserPassword().isBlank()) {
                return ResponseEntity.badRequest().body("Nombre, correo y contraseña son obligatorios.");
            }

            simpleUser.setUserLastname(null);
            simpleUser.setUserProfilePicture(null);

            service.add(simpleUser);

            return ResponseEntity.ok("Usuario registrado correctamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar el usuario: " + e.getMessage());
        }
    }

}
