package ir.maktabsharif.finalproject.repository;

import ir.maktabsharif.finalproject.entity.Course;

import ir.maktabsharif.finalproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Modifying
    //braye amalyat haye delete and ubdate estfade mishe
    @Query("UPDATE Course c SET c.teacher = null, c.students = null WHERE c.id = :courseId")
    void clearTeacherAndStudentFromCourse(Long id);

    List<Course> findByTeacher(User teacher);
}


