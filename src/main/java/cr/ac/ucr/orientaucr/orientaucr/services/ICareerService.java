package cr.ac.ucr.orientaucr.orientaucr.services;

import cr.ac.ucr.orientaucr.orientaucr.domain.Career;
import cr.ac.ucr.orientaucr.orientaucr.domain.Course;
import java.util.List;


public interface ICareerService extends CRUD<Career>{
    List<Course> getAllCourses(String curriculaId);
    
}