package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.entity.ExamStudent;

import java.util.List;

public interface ExamStudentService {
    void create(ExamStudent examStudent);

    ExamStudent findByStudentIdAndExamId(Long studentId, Long examId);

    void changeState(ExamStudent examStudent);

    ExamStudent findById(Long id);

    int getStudentMultipleScore(ExamStudent examStudent);

    List<ExamStudent> findAllByExamId(Long examId);
    Integer getStudentFinalScore(ExamStudent examStudent);
    void updateFinalScore(int totalScore, ExamStudent examStudent);
}
