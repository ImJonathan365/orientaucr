package cr.ac.ucr.orientaucr.orientaucr.services;

import cr.ac.ucr.orientaucr.orientaucr.domain.Career;
import cr.ac.ucr.orientaucr.orientaucr.domain.Course;
import java.util.List;


public interface ICareerService extends CRUD<Career>{
    List<Course> getAllCourses(String curriculaId);
    void deleteCourseFromCareer(String curriculaId, String courseId);
    List<Course> getCoursesForCurricula(String curriculaId);
    void addCourseToCurricula(String curriculaId, String courseId, int semester);
    String addNewCareerWithCorricula(Career career);
    
}