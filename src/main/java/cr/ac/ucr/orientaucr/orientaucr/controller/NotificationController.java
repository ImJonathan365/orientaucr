package cr.ac.ucr.orientaucr.orientaucr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.ac.ucr.orientaucr.orientaucr.domain.Notification;
import cr.ac.ucr.orientaucr.orientaucr.domain.NotificationAttachment;
import cr.ac.ucr.orientaucr.orientaucr.services.INotificationService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public List<Notification> getAll() {
        return notificationService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getById(@PathVariable String id) {
        Notification notification = notificationService.findById(id);
        if (notification == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notification);
    }


@PostMapping(value = "/create", consumes = {"multipart/form-data"})
public ResponseEntity<Notification> create(
        @RequestPart("notification") String notificationJson,
        @RequestPart(value = "file", required = false) MultipartFile file
) {
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        Notification notification = objectMapper.readValue(notificationJson, Notification.class);
        System.out.println("Parsed notification: " + notification);

        if (file != null && !file.isEmpty()) {
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads", "notifications");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                System.out.println("Carpeta creada: " + uploadPath.toAbsolutePath());
            }

            Path filePath = uploadPath.resolve(filename);
            file.transferTo(filePath.toFile());

            NotificationAttachment attachment = new NotificationAttachment();
            attachment.setAttachmentId(UUID.randomUUID().toString());
            attachment.setFileName(file.getOriginalFilename());
            // Guarda la ruta completa
            attachment.setFilePath(filePath.toString());
            attachment.setFileMimeType(file.getContentType());
            attachment.setFileSizeKb((int) (file.getSize() / 1024));
            attachment.setNotification(notification);

            notification.setAttachments(List.of(attachment));

            System.out.println("Attachment creado y asociado a la notificación.");
        } else {
            System.out.println("No se recibió archivo o está vacío.");
        }

        notificationService.addWithAttachments(notification, null);
        System.out.println("Notificación guardada en el servicio.");

        return ResponseEntity.ok(notification);
    } catch (Exception e) {
        System.err.println("Error en create:");
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
    }
}

@PutMapping(value = "/update/{id}", consumes = {"multipart/form-data"})
public ResponseEntity<Notification> update(
        @PathVariable String id,
        @RequestPart("notification") String notificationJson,
        @RequestPart(value = "attachments", required = false) MultipartFile[] attachments
) {
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        Notification notification = objectMapper.readValue(notificationJson, Notification.class);
        notification.setNotificationId(id);
        List<MultipartFile> attachmentList = attachments != null ? List.of(attachments) : null;
        if (attachmentList != null && !attachmentList.isEmpty()) {
            System.out.println("Archivos recibidos: " + attachmentList.size());
            for (MultipartFile file : attachmentList) {
                System.out.println("Archivo: " + file.getOriginalFilename() + ", tamaño: " + file.getSize() + " bytes");
            }
        } else {
            System.out.println("No se recibieron archivos o está vacío.");
        }

        notificationService.updateWithAttachments(notification, attachmentList);
        return ResponseEntity.ok(notification);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

    @DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable String id) {
    try {
        notificationService.deleteById(id);
        return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.notFound().build();
    }
}
}