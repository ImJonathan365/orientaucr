package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Event;
import cr.ac.ucr.orientaucr.orientaucr.services.lEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import org.springframework.http.HttpStatus;

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
    public ResponseEntity<?> addEvent(@RequestBody Event event) {
        try {
            service.add(event);
            return ResponseEntity.ok("Evento agregado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar el evento: " + e.getMessage());
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateEvent(@RequestBody Event event) {
        try {
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
