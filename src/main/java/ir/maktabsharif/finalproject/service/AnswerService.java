package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.entity.Answer;

public interface AnswerService {
    void createAnswer(Answer answer);
    Answer findByStudentIdAndQuestionId(Long studentId, Long questionId);
    Answer findById(Long id);
    void updateAnswer(Answer answer);
}
