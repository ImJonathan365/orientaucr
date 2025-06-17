package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Event;
import cr.ac.ucr.orientaucr.orientaucr.services.lEventService;
import cr.ac.ucr.orientaucr.orientaucr.utils.ImageUtils;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/events";

    private final lEventService service;

    public EventController(lEventService service) {
        this.service = service;
    }
    @PreAuthorize("hasAuthority('VER EVENTOS')")
    @GetMapping("/allEvents")
    public LinkedList<Event> getAllEvents() {
        return new LinkedList<>(service.getAll());
    }
    @PreAuthorize("hasAuthority('VER EVENTOS')")
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable String id) {
        Event event = service.findById(id);
        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('CREAR EVENTOS')")
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

                String filename = ImageUtils.saveImage(imageFile, UPLOAD_DIR);
                event.setEventImagePath(filename);
            }

            service.add(event);
            return ResponseEntity.ok("Evento agregado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar el evento: " + e.getMessage());
        }
    }
    @PreAuthorize("hasAuthority('VER EVENTOS')")
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

     @PreAuthorize("hasAnyAuthority('MODIFICAR EVENTOS', 'EDITAR EVENTOS')")
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
            Event event = service.findById(eventId);
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
                if (event.getEventImagePath() != null) {
                    ImageUtils.deleteImage(UPLOAD_DIR, event.getEventImagePath());
                }
                String filename = ImageUtils.saveImage(imageFile, UPLOAD_DIR);
                event.setEventImagePath(filename);
            }

            service.update(event);
            return ResponseEntity.ok("Evento actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el evento: " + e.getMessage());
        }
    }
   @PreAuthorize("hasAuthority('ELIMINAR EVENTOS')")
   @DeleteMapping("/delete/{id}")
public ResponseEntity<String> deleteEvent(@PathVariable String id) {
    try {
        Event event = service.findById(id);
        if (event == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento no encontrado");
        }
        String imageFilename = event.getEventImagePath();
        if (imageFilename != null && !imageFilename.isBlank()) {
            ImageUtils.deleteImage(UPLOAD_DIR, imageFilename);
        }

        service.deleteById(id);

        return ResponseEntity.ok("Evento eliminado correctamente");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al eliminar el evento: " + e.getMessage());
    }
}
 @PreAuthorize("hasAuthority('VER EVENTOS')")
@PostMapping("/interested/{eventId}/{userId}")
public ResponseEntity<String> insertUserInterestedEvent(
        @PathVariable String eventId,
        @PathVariable String userId) {
    try {
        service.InsertUserInterestedEvent(eventId, userId);
        return ResponseEntity.ok("Usuario marcado como interesado en el evento.");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al marcar interés del usuario: " + e.getMessage());
    }
}
 @PreAuthorize("hasAuthority('VER EVENTOS')")
@PostMapping("/remove/{eventId}/{userId}")
public ResponseEntity<String> removeUserInterestedEvent(
        @PathVariable String eventId,
        @PathVariable String userId) {
    try {
        service.removeUserInterestedEvent(eventId, userId);
        return ResponseEntity.ok("Usuario marcado como interesado en el evento.");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al marcar interés del usuario: " + e.getMessage());
     }
   }
}
