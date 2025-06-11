package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Event;
import cr.ac.ucr.orientaucr.orientaucr.services.lEventService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalTime;
import java.util.Date;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final lEventService service;

    public EventController(lEventService service) {
        this.service = service;
    }

    @GetMapping("/allEvents")
    public LinkedList<Event> getAllEvents() {
        return new LinkedList<>(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable String id) {
        Event event = service.findById(id);
        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST - add event
    @PostMapping("/add")
    public ResponseEntity<?> addEvent(
            @RequestParam("eventTitle") String eventTitle,
            @RequestParam("eventDescription") String eventDescription,
            @RequestParam("eventDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date eventDate,
            @RequestParam("eventTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime eventTime,
            @RequestParam("eventModality") Event.Modality eventModality,
            @RequestParam(value = "campusId", required = false) String campusId,
            @RequestParam(value = "subcampusId", required = false) String subcampusId,
            @RequestParam(value = "createdBy", required = false) String createdBy,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) {
        try {
            Event event = new Event();
            event.setEventId(UUID.randomUUID().toString());
            event.setEventTitle(eventTitle);
            event.setEventDescription(eventDescription);
            event.setEventDate(new java.sql.Date(eventDate.getTime()));
            event.setEventTime(eventTime);
            event.setEventModality(eventModality);
            event.setCampusId((campusId == null || campusId.isBlank()) ? null : campusId);
            event.setSubcampusId((subcampusId == null || subcampusId.isBlank()) ? null : subcampusId);
            event.setCreatedBy(createdBy);

            if (imageFile != null && !imageFile.isEmpty()) {
                String filename = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads", "events");
                Files.createDirectories(uploadPath);
                Path filePath = uploadPath.resolve(filename);
                imageFile.transferTo(filePath.toFile());
                event.setEventImagePath(filename);
            }

            service.add(event);

            return ResponseEntity.ok("Evento agregado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar el evento: " + e.getMessage());
        }
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path imagePath = Paths.get(System.getProperty("user.dir"), "uploads", "events", filename);
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                // Detectar tipo MIME real
                String contentType = Files.probeContentType(imagePath);
                if (contentType == null) {
                    contentType = "application/octet-stream"; // fallback si no se detecta
                }
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEvent(
            @RequestParam("eventId") String eventId,
            @RequestParam("eventTitle") String eventTitle,
            @RequestParam("eventDescription") String eventDescription,
            @RequestParam("eventDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date eventDate,
            @RequestParam("eventTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime eventTime,
            @RequestParam("eventModality") Event.Modality eventModality,
            @RequestParam(value = "campusId", required = false) String campusId,
            @RequestParam(value = "subcampusId", required = false) String subcampusId,
            @RequestParam(value = "createdBy", required = false) String createdBy,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) {
        try {
            Event event = service.findById(eventId); // <-- recuperas el evento original
            if (event == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento no encontrado");
            }

            event.setEventTitle(eventTitle);
            event.setEventDescription(eventDescription);
            event.setEventDate(new java.sql.Date(eventDate.getTime()));
            event.setEventTime(eventTime);
            event.setEventModality(eventModality);
            event.setCampusId((campusId == null || campusId.isBlank()) ? null : campusId);
            event.setSubcampusId((subcampusId == null || subcampusId.isBlank()) ? null : subcampusId);
            event.setCreatedBy(createdBy);

            if (imageFile != null && !imageFile.isEmpty()) {
                // Guardas la nueva imagen
                String filename = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads", "events");
                Files.createDirectories(uploadPath);
                Path filePath = uploadPath.resolve(filename);
                imageFile.transferTo(filePath.toFile());

                // Si quieres eliminar la imagen anterior aquí puedes agregar lógica para borrarla
                event.setEventImagePath(filename);
            }

            service.update(event);

            return ResponseEntity.ok("Evento actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el evento: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable String id) {
        try {
            service.deleteById(id);
            return ResponseEntity.ok("Evento eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el evento: " + e.getMessage());
        }
    }

}
