package ir.maktabsharif.finalproject.repository;

import ir.maktabsharif.finalproject.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Answer findByStudentIdAndQuestionId(Long studentId, Long questionId);
}
