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

        if (course.getCorequisites() != null && !course.getCorequisites().isEmpty()){
            Set<Course> corequisites = course.getCorequisites().stream()
                    .map(coreq -> findById(coreq.getCourseId()))
                    .collect(Collectors.toSet());
            course.setCorequisites(corequisites);
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

        if (t.getCorequisites() != null) {
            Set<Course> corequisites = t.getCorequisites().stream()
                    .map(coreq -> courseRepo.findById(coreq.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Correquisito no encontrado: "+ coreq.getCourseId())))
                    .collect(Collectors.toSet());
            existing.setCorequisites(corequisites);
        } else {
            existing.setCorequisites(Collections.emptySet());
        };



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

        for (Course corequisite : new HashSet<>(course.getCorequisites())) {
            corequisite.getDependentCorequisites().remove(course);
        }
        course.getCorequisites().clear();

         for (Course dependentCoreq : new HashSet<>(course.getDependentCorequisites())) {
            dependentCoreq.getCorequisites().remove(course);
        }
        course.getDependentCorequisites().clear();

        course.getCurriculumCourses().clear();

        courseRepo.delete(course);
    }

    @Override
    @Transactional
    public Course findById(String id) {
        return courseRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Curso con " + id + " no existe"));
    }

   
}
