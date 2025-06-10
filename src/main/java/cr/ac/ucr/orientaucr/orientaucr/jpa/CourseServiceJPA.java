
package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.Course;
import cr.ac.ucr.orientaucr.orientaucr.repository.ICourseRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.ICourseService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceJPA implements ICourseService{
    
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
    public void add(Course t) {
        t.setCourseId(UUID.randomUUID().toString());
        courseRepo.save(t);
    }

    @Override
    public void update(Course t) {
        Course existing = courseRepo.findById(t.getCourseId()).orElseThrow();
        existing.setCourseCode(t.getCourseCode());
        existing.setCourseCredits(t.getCourseCredits());
        existing.setCourseName(t.getCourseName());
        existing.setCourseDescription(t.getCourseDescription());
        courseRepo.save(existing);
    }

    @Override
    public void deleteById(String i) {
        courseRepo.deleteById(i);
    }

    @Override
    public Course findById(String i) {
        return courseRepo.findById(i).get();
    }
    
}
