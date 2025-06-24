package cr.ac.ucr.orientaucr.orientaucr.repository;

import cr.ac.ucr.orientaucr.orientaucr.domain.Course;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRepository extends JpaRepository<Course, String> {

    @Procedure(procedureName = "sp_get_courses_by_curricula")
    List<Course> findByCurriculaId(@Param("p_curricula_id") String curriculaId);

    @Procedure(procedureName = "sp_remove_course_from_curriculum")
    void deleteCourseFromCareer(@Param("p_curricula_id") String curriculaId, @Param("p_course_id") String courseId);

    @Procedure(procedureName = "sp_get_courses_not_in_curriculum")
    List<Course> getCoursesForCurricula(@Param("p_curricula_id") String curriculaId);

    @Procedure(procedureName = "sp_add_course_to_curriculum")
    void addCourseToCurricula(@Param("p_curricula_id") String curriculaId, @Param("p_course_id") String courseId, @Param("p_course_semester") int semester);

    @Query(value = "SELECT COUNT(DISTINCT cu.career_id) FROM curriculum_courses cc JOIN curricula cu ON cc.curricula_id = cu.curricula_id WHERE cc.course_id = :courseId", nativeQuery = true)
    int numberCarrersAssociated(@Param("courseId") String courseId);
}
