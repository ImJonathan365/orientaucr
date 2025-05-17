package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Characteristic;
import cr.ac.ucr.orientaucr.orientaucr.service.CharacteristicService;
import java.util.LinkedList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/characteristic")
public class CharacteristicController {
    
    private final CharacteristicService service = new CharacteristicService();

    @GetMapping("/all")
    public LinkedList<Characteristic> getAll() {
        return service.getAllCharacteristic();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Characteristic> getById(@PathVariable String id) {
        Characteristic c = service.findCharacteristicById(id);
        if (c != null) {
            return ResponseEntity.ok(c);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Characteristic c) {
        service.addCharacteristic(c);
        return ResponseEntity.ok("Característica agregada correctamente.");
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody Characteristic c) {
        service.updateCharacteristic(c);
        return ResponseEntity.ok("Característica actualizada correctamente.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        service.deleteCharacteristicById(id);
        return ResponseEntity.ok("Característica eliminada correctamente.");
    }
    
}