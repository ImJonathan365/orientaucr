package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import cr.ac.ucr.orientaucr.orientaucr.security.JwtUtil;
import cr.ac.ucr.orientaucr.orientaucr.services.IUserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IUserService service;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        try {
            if (loginUser.getUserEmail() == null || loginUser.getUserPassword() == null) {
                return ResponseEntity.badRequest().body("Correo y contraseña son obligatorios.");
            }
            User user = service.authenticateUser(loginUser.getUserEmail(), loginUser.getUserPassword());
            if (user != null) {
                List<String> permissions = user.getUserRoles().stream()
                        .flatMap(role -> role.getPermissions().stream())
                        .map(permission -> permission.getPermissionName())
                        .collect(Collectors.toList());
                String token = jwtUtil.generateToken(user.getUserEmail(), permissions);
                user.setJwtToken(token);
                service.updateUserToken(user.getUserId(), token);
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al autenticar el usuario: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            if (user.getUserName() == null || user.getUserName().isBlank()
                    || user.getUserEmail() == null || user.getUserEmail().isBlank()
                    || user.getUserPassword() == null || user.getUserPassword().isBlank()) {
                return ResponseEntity.badRequest().body("Nombre, correo y contraseña son obligatorios.");
            }

            if (service.findByEmail(user.getUserEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("El correo ya está registrado.");
            }

            user.setUserLastname(null);
            user.setUserProfilePicture(null);

            List<String> permissions = user.getUserRoles().stream()
                    .flatMap(role -> role.getPermissions().stream())
                    .map(permission -> permission.getPermissionName())
                    .collect(Collectors.toList());
            String token = jwtUtil.generateToken(user.getUserEmail(), permissions);
            user.setJwtToken(token);
            service.add(user);

            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar el usuario: " + e.getMessage());
        }
    }

}
