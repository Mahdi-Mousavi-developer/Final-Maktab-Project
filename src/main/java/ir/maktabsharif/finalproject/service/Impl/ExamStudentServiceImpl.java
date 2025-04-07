package ir.maktabsharif.finalproject.service.Impl;

import ir.maktabsharif.finalproject.entity.*;
import ir.maktabsharif.finalproject.repository.ExamStudentRepository;
import ir.maktabsharif.finalproject.service.AnswerService;
import ir.maktabsharif.finalproject.service.ExamStudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamStudentServiceImpl implements ExamStudentService {
    private final ExamStudentRepository examStudentRepository;
    private final AnswerService answerService;

    @Autowired
    public ExamStudentServiceImpl(ExamStudentRepository examStudentRepository, AnswerService answerService) {
        this.examStudentRepository = examStudentRepository;
        this.answerService = answerService;
    }

    @Override
    public void create(ExamStudent examStudent) {
        ExamStudent examStudent1 = new ExamStudent();
        examStudent1.setStudent(examStudent.getStudent());
        examStudent1.setExam(examStudent.getExam());
        examStudent1.setExamStudentState(ExamStudentState.IN_PROGRESS);
        examStudent1.setFinalScore(examStudent.getFinalScore());
        examStudent1.setStartDate(examStudent.getStartDate());
        examStudent1.setEndDate(examStudent.getEndDate());
        examStudentRepository.save(examStudent1);

    }

    @Override
    public ExamStudent findByStudentIdAndExamId(Long studentId, Long examId) {
        return examStudentRepository.findByStudentIdAndExamId(studentId, examId);
    }

    @Override
    public void changeState(ExamStudent examStudent) {
        ExamStudent examStudent1 = this.findById(examStudent.getId());
        examStudent1.setExamStudentState(examStudent.getExamStudentState());
        examStudentRepository.save(examStudent1);
    }

    @Override
    public ExamStudent findById(Long id) {
        return examStudentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("studentExam not found"));
    }

    @Override
    public int getStudentMultipleScore(ExamStudent examStudent) {
        Exam exam = examStudent.getExam();
        Long studentId = examStudent.getStudent().getId();
        int totalMultipleScore =0;
        List<Question> questions = exam.getQuestions();
        for (Question question : questions) {
            if (question.getQuestionType().equals(QuestionType.MULTIPLE_CHOICE)) {
                Optional<Answer> answer = Optional.ofNullable(answerService.findByStudentIdAndQuestionId(studentId, question.getId()));
                if (answer.isPresent()){
                    totalMultipleScore += answer.get().getStudentScore();
                }

            }
        }
        examStudent.setFinalScore(totalMultipleScore);
        examStudentRepository.save(examStudent);
        return totalMultipleScore;
    }

    @Override
    public List<ExamStudent> findAllByExamId(Long examId) {
        return examStudentRepository.findAllByExamId(examId);
    }

    @Override
    public Integer getStudentFinalScore(ExamStudent examStudent) {
        Exam exam = examStudent.getExam();
        Long studentId = examStudent.getStudent().getId();
        int totalMultipleScore =0;
        List<Question> questions = exam.getQuestions();
        for (Question question : questions) {
            {
                Optional<Answer> answer = Optional.ofNullable(answerService.findByStudentIdAndQuestionId(studentId, question.getId()));
                if (answer.isPresent()){
                    totalMultipleScore += answer.get().getStudentScore();
                }

            }
        }
        examStudent.setFinalScore(totalMultipleScore);
        examStudentRepository.save(examStudent);
        return totalMultipleScore;

    }

    @Override
    public void updateFinalScore(int totalScore, ExamStudent examStudent) {
        ExamStudent examStudent1 = this.findById(examStudent.getId());
        examStudent1.setFinalScore(totalScore);
        examStudentRepository.save(examStudent1);
    }
}
