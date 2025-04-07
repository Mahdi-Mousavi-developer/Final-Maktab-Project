package ir.maktabsharif.finalproject.repository;

import ir.maktabsharif.finalproject.entity.Course;
import ir.maktabsharif.finalproject.entity.Exam;
import ir.maktabsharif.finalproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByCourse(Course course);
    List<Exam> findByCourseId(Long courseId);


}
