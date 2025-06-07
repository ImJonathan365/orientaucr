package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Career;
import cr.ac.ucr.orientaucr.orientaucr.services.ICareerService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ICareerService careerService;
    
    @GetMapping("/listCareers")
    public String page(Model model) {
        model.addAttribute("career", careerService.getAll());
        return "careers/listCareers";
    }

    // Nuevo endpoint para API REST
    @RequestMapping("/list")
    @ResponseBody
    public ResponseEntity<LinkedList<Career>> getAllCareers() {
        return ResponseEntity.ok(careerService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addCareer(@RequestBody Career career) {
        careerService.add(career);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateCareer(@RequestBody Career career) {
        careerService.update(career);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{career_id}")
    public ResponseEntity<Void> deleteCareer(@PathVariable("career_id") String careerId) {
        try {
            careerService.deleteById(careerId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/searchCareer/{career_id}")
    public ResponseEntity<Career> getCareerById(@PathVariable("career_id") String careerId) {
        try {
            Career career = careerService.findById(careerId);
            
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