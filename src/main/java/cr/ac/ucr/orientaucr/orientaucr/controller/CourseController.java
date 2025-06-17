package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Course;
import cr.ac.ucr.orientaucr.orientaucr.services.ICourseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private ICourseService courseService;

    @PreAuthorize("hasAuthority('VER CURSOS')")
    @RequestMapping("/list")
    @ResponseBody
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAll());
    }

    @PreAuthorize("hasAuthority('CREAR CURSOS')")
    @PostMapping("/add")
    public ResponseEntity<Void> addCourse(@RequestBody Course career) {
        courseService.add(career);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAuthority('MODIFICAR CURSOS')")
    @PostMapping("/update")
    public ResponseEntity<Void> updateCourse(@RequestBody Course course) {
        courseService.update(course);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAuthority('ELIMINAR CURSOS')")
    @DeleteMapping("/delete/{course_id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("course_id") String courseId) {
        try {
            courseService.deleteById(courseId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAuthority('VER CURSOS')")
    @GetMapping("/searchCourse/{course_id}")
    public ResponseEntity<Course> getCourseById(@PathVariable("course_id") String courseId) {
        try {
            Course course = courseService.findById(courseId);

            if (course != null) {
                return ResponseEntity.ok(course);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
