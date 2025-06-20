package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Permission;
import cr.ac.ucr.orientaucr.orientaucr.domain.Roles;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import cr.ac.ucr.orientaucr.orientaucr.security.JwtUtil;
import cr.ac.ucr.orientaucr.orientaucr.services.IUserService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.hibernate.Hibernate;
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
                String refreshToken = jwtUtil.generateRefreshToken(user.getUserEmail(), permissions);
                user.setJwtToken(token);
                service.updateUserToken(user.getUserId(), token);
                return ResponseEntity.ok(Map.of("token", token, "refreshToken", refreshToken));
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
            service.add(user);
            User savedUser = service.findByEmail(user.getUserEmail())
                    .orElseThrow(() -> new IllegalStateException("Usuario no encontrado después de registrarse"));
            
            List<Roles> roles = service.getRolesByEmail(savedUser.getUserEmail());
            savedUser.setUserRoles(roles);
            
            List<String> permissions = savedUser.getUserRoles().stream()
                    .flatMap(role -> role.getPermissions().stream())
                    .map(permission -> permission.getPermissionName())
                    .collect(Collectors.toList());
            
            String token = jwtUtil.generateToken(savedUser.getUserEmail(), permissions);
            String refreshToken = jwtUtil.generateRefreshToken(savedUser.getUserEmail(), permissions);
            savedUser.setJwtToken(token);
            service.updateUserToken(savedUser.getUserId(), token);

            return ResponseEntity.ok(Map.of("token", token, "refreshToken", refreshToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar el usuario: " + e.getMessage());
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        if (refreshToken == null || !jwtUtil.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token inválido.");
        }
        String userEmail = jwtUtil.extractUsername(refreshToken);
        User user = service.findByEmail(userEmail).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado.");
        }
        List<String> permissions = user.getUserRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(permission -> permission.getPermissionName())
                .collect(Collectors.toList());
        String newToken = jwtUtil.generateToken(userEmail, permissions);
        String newRefreshToken = jwtUtil.generateRefreshToken(userEmail, permissions);
        service.updateUserToken(user.getUserId(), newToken);
        return ResponseEntity.ok(Map.of("token", newToken, "refreshToken", newRefreshToken));
    }

}
