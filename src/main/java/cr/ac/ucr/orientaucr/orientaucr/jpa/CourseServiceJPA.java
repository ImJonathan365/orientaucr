package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.Course;
import cr.ac.ucr.orientaucr.orientaucr.repository.ICourseRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.ICourseService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseServiceJPA implements ICourseService {

    @Autowired
    private ICourseRepository courseRepo;

    @Override
    public List<Course> getAll(String search) {
        return null;
    }

    @Override
    public List<Course> getAll() {
        return courseRepo.findAll();
    }

    @Override
    @Transactional
    public void add(Course course) {
        course.setCourseId(UUID.randomUUID().toString());

        if (course.getPrerequisites() != null && !course.getPrerequisites().isEmpty()) {
            Set<Course> prerequisites = course.getPrerequisites().stream()
                    .map(prereq -> findById(prereq.getCourseId()))
                    .collect(Collectors.toSet());
            course.setPrerequisites(prerequisites);
        }

        courseRepo.save(course);
    }

    @Override
    @Transactional
    public void update(Course t) {
        Course existing = courseRepo.findById(t.getCourseId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        existing.setCourseCode(t.getCourseCode());
        existing.setCourseCredits(t.getCourseCredits());
        existing.setCourseIsShared(t.isCourseIsShared());
        existing.setCourseIsAsigned(t.isCourseIsAsigned());
        existing.setCourseName(t.getCourseName());
        existing.setCourseDescription(t.getCourseDescription());
        existing.setCourseSemester(t.getCourseSemester());

        
        if (t.getPrerequisites() != null) {
            Set<Course> prerequisites = t.getPrerequisites().stream()
                    .map(prereq -> courseRepo.findById(prereq.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Prerrequisito no encontrado: " + prereq.getCourseId())))
                    .collect(Collectors.toSet());
            existing.setPrerequisites(prerequisites);
        } else {
            existing.setPrerequisites(Collections.emptySet());
        }

        courseRepo.save(existing);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Course course = courseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        for (Course dependent : new HashSet<>(course.getDependentCourses())) {
            dependent.getPrerequisites().remove(course);
        }
        course.getDependentCourses().clear();

        for (Course prerequisite : new HashSet<>(course.getPrerequisites())) {
            prerequisite.getDependentCourses().remove(course);
        }
        course.getPrerequisites().clear();

        course.getCurriculumCourses().clear();

        courseRepo.delete(course);
    }

    @Override
    @Transactional
    public Course findById(String id) {
        return courseRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Curso con " + id + " no existe"));
    }

   
}
