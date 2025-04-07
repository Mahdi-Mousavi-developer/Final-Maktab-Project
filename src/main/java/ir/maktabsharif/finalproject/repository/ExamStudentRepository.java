package ir.maktabsharif.finalproject.repository;

import ir.maktabsharif.finalproject.entity.ExamStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamStudentRepository extends JpaRepository<ExamStudent, Long> {
ExamStudent findByStudentIdAndExamId(Long studentId, Long examId);

    List<ExamStudent> findAllByExamId(Long examId);
}
