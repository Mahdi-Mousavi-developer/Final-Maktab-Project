package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.entity.Answer;
import ir.maktabsharif.finalproject.entity.Question;

import java.util.List;

public interface QuestionService {
    List<Question> findByCourseAndTeacherId(Long courseId, Long teacherId ,Long examId);
    Question findById(Long id);
    List<Question> findByExamId(Long examId);
    void deleteById(Long id);
    void updateQuestion(Long id, Question question);
    void addAnswerToQuestion(Long questionId, Answer answer);
}
