package cr.ac.ucr.orientaucr.orientaucr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.ac.ucr.orientaucr.orientaucr.domain.Notification;
import cr.ac.ucr.orientaucr.orientaucr.domain.NotificationAttachment;
import cr.ac.ucr.orientaucr.orientaucr.services.INotificationService;
import cr.ac.ucr.orientaucr.orientaucr.utils.AttachmentValidator;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private INotificationService notificationService;

    private AttachmentValidator attv = new AttachmentValidator();

    @PreAuthorize("hasAuthority('VER NOTIFICACIONES')")
    @GetMapping("/list")
    public ResponseEntity<List<Notification>> getAll() {
        try {
            List<Notification> notifications = notificationService.getAll();
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PreAuthorize("hasAuthority('VER NOTIFICACIONES')")
    @GetMapping("/find/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        try {
            if (id == null || id.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ID de la notificación es obligatorio.");
            }
            Notification notification = notificationService.findById(id);
            if (notification == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la notificación");
            }
            return ResponseEntity.ok(notification);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener la notificación.");
        }
    }

    @PreAuthorize("hasAuthority('CREAR NOTIFICACIONES')")
    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<String> create(
            @RequestPart("notification") String notificationJson,
            @RequestPart(value = "files", required = false) MultipartFile[] files
    ) {
        List<File> tempFiles = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Notification notification;
            try {
                notification = objectMapper.readValue(notificationJson, Notification.class);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El formato del JSON de la notificación es inválido.");
            }
            String validationError = validateNotification(notification, false);
            if (validationError != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
            }
            List<NotificationAttachment> attachments = new ArrayList<>();
            tempFiles = new ArrayList<>();
            try {
                attv.processAttachments(notification, files, attachments, tempFiles);
            } catch (IllegalArgumentException iae) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(iae.getMessage());
            }
            notification.setAttachments(attachments);
            notificationService.add(notification);
            return ResponseEntity.ok("Notificación creada correctamente.");
        } catch (Exception e) {
            attv.cleanTempFiles(tempFiles);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la notificación.");
        }
    }

    @PreAuthorize("hasAuthority('MODIFICAR NOTIFICACIONES')")
    @PutMapping(value = "/update", consumes = {"multipart/form-data"})
    public ResponseEntity<String> update(
            @RequestPart("notification") String notificationJson,
            @RequestPart(value = "files", required = false) MultipartFile[] attachmentsFiles
    ) {
        List<File> tempFiles = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Notification notification;
            try {
                notification = objectMapper.readValue(notificationJson, Notification.class);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El formato del JSON de la notificación es inválido.");
            }
            String validationError = validateNotification(notification, true);
            if (validationError != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
            }
            Notification existing = notificationService.findById(notification.getNotificationId());
            if (existing == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la notificación.");
            }
            if (existing.isSent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede actualizar una notificación ya enviada.");
            }            
            List<NotificationAttachment> attachments = new ArrayList<>();
            tempFiles = new ArrayList<>();
            try {
                attv.processAttachments(notification, attachmentsFiles, attachments, tempFiles);
            } catch (IllegalArgumentException iae) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(iae.getMessage());
            }
            List<NotificationAttachment> oldAttachments = existing.getAttachments();
            if (oldAttachments != null && !oldAttachments.isEmpty()) {
                attv.deleteAttachments(oldAttachments);
            }
            notification.setAttachments(attachments);
            notificationService.update(notification);
            return ResponseEntity.ok("Notificación actualizada correctamente.");
        } catch (Exception e) {
            attv.cleanTempFiles(tempFiles);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la notificación.");
        }
    }

    @PreAuthorize("hasAuthority('ELIMINAR NOTIFICACIONES')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        try {
            if (id == null || id.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("ID de la notificación requerido.");
            }
            Notification notification = notificationService.findById(id);
            if (notification == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notificación no encontrada.");
            }
            if (notification.getAttachments() != null && !notification.getAttachments().isEmpty()) {
                AttachmentValidator.deleteAttachments(notification.getAttachments());
            }
            notificationService.deleteById(id);
            return ResponseEntity.ok("Notificación eliminada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar notificación.");
        }
    }

    private String validateNotification(Notification notification, boolean checkId) {
        if (checkId && (notification.getNotificationId() == null || notification.getNotificationId().trim().isEmpty())) {
            return "El ID de la notificación es obligatorio.";
        }
        if (notification.getNotificationTitle() == null || notification.getNotificationTitle().trim().isEmpty()) {
            return "El título es obligatorio.";
        }
        if (notification.getNotificationSendDate() == null) {
            return "La fecha de envío de la notificación es obligatoria.";
        }
        if (notification.getSenderId() == null || notification.getSenderId().trim().isEmpty()) {
            return "El ID del remitente es obligatorio.";
        }
        return null;
    }

}
