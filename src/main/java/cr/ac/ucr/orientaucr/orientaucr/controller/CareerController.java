package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Career;
import cr.ac.ucr.orientaucr.orientaucr.service.CareerService;
import java.util.LinkedList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/career")
public class CareerController {

    @GetMapping("/listCareers")
    public String page(Model model) {
        model.addAttribute("career", CareerService.getAllCareers());
        return "careers/listCareers";
    }

    // Nuevo endpoint para API REST
    @RequestMapping("/list")
    @ResponseBody
    public ResponseEntity<LinkedList<Career>> getAllCareers() {
        return ResponseEntity.ok(CareerService.getAllCareers());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addCareer(@RequestBody Career career) {
        CareerService.addCareer(career);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateCareer(@RequestBody Career career) {
        CareerService.updateCareer(career);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{career_id}")
    public ResponseEntity<Void> deleteCareer(@PathVariable("career_id") String careerId) {
        try {
            CareerService.deleteCareer(careerId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/searchCareer/{career_id}")
    public ResponseEntity<Career> getCareerById(@PathVariable("career_id") String careerId) {
        try {
            Career career = CareerService.findByIdCareer(careerId);
            
            if (career != null) {
                return ResponseEntity.ok(career);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
