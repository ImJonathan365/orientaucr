package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.EmailTemplate;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import cr.ac.ucr.orientaucr.orientaucr.security.JwtUtil;
import cr.ac.ucr.orientaucr.orientaucr.services.EmailService;
import cr.ac.ucr.orientaucr.orientaucr.services.IEmailTemplateService;
import cr.ac.ucr.orientaucr.orientaucr.services.IUserService;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IUserService service;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    @Autowired
    private IEmailTemplateService emailTemplateService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        try {
            if (loginUser.getUserEmail() == null || loginUser.getUserEmail().isBlank()) {
                return ResponseEntity.badRequest().body("El correo es obligatorio.");
            }
            if (loginUser.getUserPassword() == null || loginUser.getUserPassword().isBlank()) {
                return ResponseEntity.badRequest().body("La contraseña es obligatoria.");
            }
            User user = service.authenticateUser(loginUser.getUserEmail(), loginUser.getUserPassword());
            if (user != null) {
                if (!user.isIsEmailVerified()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("El correo no ha sido verificado.");
                }
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
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas.");
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
            Optional<User> existingUserOpt = service.findByEmail(user.getUserEmail());
            User targetUser;

            if (existingUserOpt.isPresent()) {
                User existingUser = existingUserOpt.get();

                if (existingUser.isIsEmailVerified()) {
                    return ResponseEntity.badRequest().body("El correo ya está registrado y verificado.");
                } else {
                    existingUser.setUserName(user.getUserName());
                    existingUser.setUserPassword(user.getUserPassword());
                    existingUser.setUserLastname(user.getUserLastname());
                    existingUser.setUserProfilePicture(user.getUserProfilePicture());
                    service.update(existingUser);
                    targetUser = existingUser;
                }
            } else {
                user.setIsEmailVerified(false);
                service.add(user);
                targetUser = service.findByEmail(user.getUserEmail())
                        .orElseThrow(() -> new IllegalStateException("Usuario no encontrado después de registrarse"));
            }

            String verificationToken = jwtUtil.generateToken(targetUser.getUserEmail(), List.of("VERIFY_EMAIL"), 24 * 60 * 60 * 1000);
            service.updateUserToken(targetUser.getUserId(), verificationToken);

            EmailTemplate template = emailTemplateService.findByTemplateName("VERIFICAR CORREO");
            if (template == null || !template.isIsActive()) {
                throw new IllegalStateException("Plantilla de verificación no encontrada o inactiva");
            }

            String verificationUrl = "/verify-email?token=" + verificationToken;
            String emailBody = template.getTemplateBody()
                    .replace("{name}", targetUser.getUserName())
                    .replace("{verificationUrl}", verificationUrl);

            List<File> attachments = template.getAttachments().stream()
                    .map(attachment -> new File(attachment.getFilePath()))
                    .filter(File::exists)
                    .collect(Collectors.toList());

            emailService.sendEmailWithAttachment(targetUser.getUserEmail(), template.getTemplateSubject(), emailBody, attachments);

            String responseMessage = existingUserOpt.isPresent()
                    ? "Datos actualizados. Te hemos enviado un nuevo correo de verificación."
                    : "Usuario registrado. Por favor, verifica tu correo electrónico.";

            return ResponseEntity.ok(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar el usuario: " + e.getMessage());
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        try {
            if (token == null || token.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Token de verificación requerido.");
            }
            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token de verificación inválido o expirado.");
            }
            String userEmail = jwtUtil.extractUsername(token);
            User user = service.findByEmail(userEmail)
                    .orElseThrow(() -> new IllegalStateException("Usuario no encontrado."));
            
            String message = "";
            if (user.isIsEmailVerified()) {
                message = "El correo ya está verificado.";
            } else {
                service.verifyUserEmail(user.getUserId());
                message = "Correo verificado correctamente.";
            }

            List<String> permissions = user.getUserRoles().stream()
                    .flatMap(role -> role.getPermissions().stream())
                    .map(permission -> permission.getPermissionName())
                    .collect(Collectors.toList());
            String newToken = jwtUtil.generateToken(userEmail, permissions);
            String newRefreshToken = jwtUtil.generateRefreshToken(userEmail, permissions);
            service.updateUserToken(user.getUserId(), newToken);

            return ResponseEntity.ok(Map.of(
                    "message", message,
                    "token", newToken,
                    "refreshToken", newRefreshToken
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar el correo.");
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

    /*@GetMapping("/template/{templateName}")
    public ResponseEntity<EmailTemplate> getTemplate(@PathVariable String templateName) {
        try {
            EmailTemplate template = emailTemplateService.findByTemplateName(templateName);
            return ResponseEntity.ok(template);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }*/
}
