package cr.ac.ucr.orientaucr.orientaucr.jpa;

import cr.ac.ucr.orientaucr.orientaucr.domain.Career;
import cr.ac.ucr.orientaucr.orientaucr.domain.Course;
import cr.ac.ucr.orientaucr.orientaucr.domain.Curricula;
import cr.ac.ucr.orientaucr.orientaucr.domain.CurriculumCourse;
import cr.ac.ucr.orientaucr.orientaucr.domain.CurriculumCourseKey;
import cr.ac.ucr.orientaucr.orientaucr.repository.ICareerRepository;
import cr.ac.ucr.orientaucr.orientaucr.repository.ICharacteristicRepository;
import cr.ac.ucr.orientaucr.orientaucr.repository.ICourseRepository;
import cr.ac.ucr.orientaucr.orientaucr.repository.ICurriculaRepository;
import cr.ac.ucr.orientaucr.orientaucr.services.ICareerService;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CareerServiceJPA implements ICareerService {

    @Autowired
    private ICareerRepository careerRepo;

    @Autowired
    private ICharacteristicRepository characteristicRepo;

    @Autowired
    private ICurriculaRepository curriculaRepo;

    @Autowired
    private ICourseRepository courseRepo;

    @Override
    public List<Career> getAll(String search) {
        return null;
    }

    @Override
    public List<Career> getAll() {
        return careerRepo.findAll();
    }

    @Override
    @Transactional
    public void add(Career t) {
        t.setCareerId(UUID.randomUUID().toString());
        t.setCurricula(new Curricula());
        t.getCurricula().setCurriculaId(UUID.randomUUID().toString());
        t.getCurricula().setCareer(t);
        careerRepo.save(t);
    }

    @Override
    public void update(Career t) {
        Career existing = careerRepo.findById(t.getCareerId()).orElseThrow();
        existing.setCareerName(t.getCareerName());
        existing.setCareerDescription(t.getCareerDescription());
        existing.setCareerDurationYears(t.getCareerDurationYears());
        careerRepo.save(existing);
    }

    @Override
    @Transactional
    public void deleteById(String i) {
        careerRepo.deleteById(i);
    }

    @Override
    public Career findById(String i) {
        return careerRepo.findById(i).get();
    }

    @Override
    @Transactional
    public List<Course> getAllCourses(String curriculaId) {
        return courseRepo.findByCurriculaId(curriculaId);
    }

    @Override
    @Transactional
    public void deleteCourseFromCareer(String curriculaId, String courseId) {
        courseRepo.deleteCourseFromCareer(curriculaId, courseId);
    }

    @Override
    @Transactional
    public List<Course> getCoursesForCurricula(String curriculaId) {
        return courseRepo.getCoursesForCurricula(curriculaId);
    }

    @Override
    @Transactional
    public void addCourseToCurricula(String curriculaId, String courseId, int semester) {
        courseRepo.addCourseToCurricula(curriculaId, courseId, semester);
    }

    @Override
    public String addNewCareerWithCorricula(Career career) {
        career.setCareerId(UUID.randomUUID().toString());
        Curricula curricula = new Curricula();
        curricula.setCurriculaId(UUID.randomUUID().toString());
        curricula.setCareer(career);
        career.setCurricula(curricula);
        careerRepo.save(career);

        return curricula.getCurriculaId();
    }

}
