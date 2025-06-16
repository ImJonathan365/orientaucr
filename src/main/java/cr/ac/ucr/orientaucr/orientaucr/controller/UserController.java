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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserService service;

    private ImageUtils imageService = new ImageUtils();

    private final String dirUser = System.getProperty("user.dir");

    @PreAuthorize("hasAuthority('VER USUARIOS')")
    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam("userId") String userId) {
        try {
            if (userId == null || userId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            List<User> users = service.getAllExcept(userId);
            if (users.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PreAuthorize("hasAuthority('VER USUARIOS')")
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam("search") String search, @RequestParam("userId") String userId) {
        try {
            if (userId == null || userId.trim().isEmpty() || search == null || search.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            List<User> users = service.searchAllExcept(search, userId);
            if (users.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PreAuthorize("hasAuthority('CREAR USUARIOS')")
    @PostMapping("/add")
    public ResponseEntity<String> addUser(
            @RequestPart("user") String userJson,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) {
        String imagePath = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(userJson, User.class);

            if (service.findByEmail(user.getUserEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("El correo ya está registrado.");
            }

            if (imageFile != null && !imageFile.isEmpty()) {
                imagePath = imageService.saveProfilePicture(imageFile, dirUser + File.separator + "Uploads" + File.separator + "users");
                user.setUserProfilePicture(imagePath);
            }

            service.add(user);
            return ResponseEntity.ok("Usuario agregado correctamente");
        } catch (Exception e) {
            if (imagePath != null) {
                imageService.deleteImage(dirUser + File.separator + "Uploads" + File.separator + "users", imagePath);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar usuario: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('MODIFICAR USUARIOS', 'EDITAR PERFIL')")
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

            String authenticatedEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!authenticatedEmail.equals(existingUser.getUserEmail())
                    && !SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                            .contains(new SimpleGrantedAuthority("MODIFICAR USUARIOS"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para editar este usuario.");
            }

            oldImage = existingUser.getUserProfilePicture();

            if (imageFile != null && !imageFile.isEmpty()) {
                newImagePath = imageService.saveProfilePicture(imageFile, dirUser + File.separator + "Uploads" + File.separator + "users");
                updatedUser.setUserProfilePicture(newImagePath);
            } else {
                updatedUser.setUserProfilePicture(oldImage);
            }

            service.update(updatedUser);
            if (newImagePath != null && oldImage != null && !oldImage.equals(newImagePath)) {
                imageService.deleteImage(dirUser + File.separator + "Uploads" + File.separator + "users", oldImage);
            }
            return ResponseEntity.ok("Usuario actualizado correctamente");

        } catch (Exception e) {
            if (newImagePath != null) {
                imageService.deleteImage(dirUser + File.separator + "Uploads" + File.separator + "users", newImagePath);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar usuario: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ELIMINAR USUARIOS')")
    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<String> deleteUser(@PathVariable("user_id") String userId) {
        try {
            if (userId == null || userId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("ID de usuario requerido.");
            }

            User existingUser = service.findById(userId);
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }

            service.deleteById(userId);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el usuario: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('VER USUARIO')")
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

}
