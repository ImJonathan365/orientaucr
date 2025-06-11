package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Career;
import cr.ac.ucr.orientaucr.orientaucr.domain.Characteristic;
import cr.ac.ucr.orientaucr.orientaucr.domain.Course;
import cr.ac.ucr.orientaucr.orientaucr.services.ICareerService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public ResponseEntity<List<Career>> getAllCareers() {
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

    @RequestMapping("/listByCurricula/{curricula_id}")
    @ResponseBody
    public ResponseEntity<List<Course>> getAllCourses(@PathVariable("curricula_id") String curriculaId) {
        return ResponseEntity.ok(careerService.getAllCourses(curriculaId));
    }

    @DeleteMapping("/deleteCourse/{curricula_id}/{course_id}")
    public ResponseEntity<Void> deleteCourseFromCareer(@PathVariable("curricula_id") String curriculaId, @PathVariable("course_id") String courseId) {
        try {
            careerService.deleteCourseFromCareer(curriculaId, courseId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping("/listCoursesForCurricula/{curricula_id}")
    @ResponseBody
    public ResponseEntity<List<Course>> getCoursesForCurricula(@PathVariable("curricula_id") String curriculaId) {
        return ResponseEntity.ok(careerService.getCoursesForCurricula(curriculaId));
    }

    @PostMapping("/addCourse")
    public ResponseEntity<Void> addCourseToCareer(@RequestBody Map<String, Object> body) {
        String curriculaId = (String) body.get("curriculaId");
        String courseId = (String) body.get("courseId");
        int semester = ((Number) body.get("semester")).intValue();
        careerService.addCourseToCurricula(curriculaId, courseId, semester);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/addCareer")
    public ResponseEntity<Map<String, String>> addNewCareerWithCorricula(@RequestBody Career career) {
        String curriculaId = careerService.addNewCareerWithCorricula(career);
        Map<String, String> response = new HashMap<>();
        response.put("curriculaId", curriculaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
