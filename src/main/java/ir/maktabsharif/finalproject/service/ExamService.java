package ir.maktabsharif.finalproject.service;

import ir.maktabsharif.finalproject.entity.Course;
import ir.maktabsharif.finalproject.entity.Dto.ExamRequestDto;
import ir.maktabsharif.finalproject.entity.Exam;
import ir.maktabsharif.finalproject.entity.Question;
import ir.maktabsharif.finalproject.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;



public interface ExamService {
    void saveExam(ExamRequestDto examRequestDto);
    void updateExam(Long id ,ExamRequestDto examRequestDto) throws EntityNotFoundException;
    void deleteExamById(Long id) throws EntityNotFoundException;
    List<Exam> getExamList();
    Exam getExamById(Long id) throws EntityNotFoundException;
    List<Exam> findExamByCourse(Course course);
    List<Exam> findExamsByCourseAndTeacher(Long teacherId , Course course);
    void updateExamWithoutDto(Exam exam);
    void addQuestionToExam(Question question , Exam exam);
    int setScore(Long examId) throws EntityNotFoundException;
}
