package ir.maktabsharif.finalproject.service.Impl;

import ir.maktabsharif.finalproject.entity.Answer;
import ir.maktabsharif.finalproject.repository.AnswerRepository;
import ir.maktabsharif.finalproject.service.AnswerService;
import ir.maktabsharif.finalproject.service.QuestionService;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    public AnswerServiceImpl(AnswerRepository answerRepository, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    @Override
    public void createAnswer(Answer answer) {
        Answer answer1 = new Answer();
        answer1.setStudentAnswer(answer.getStudentAnswer());
        answer1.setStudentScore(answer.getStudentScore());
        answer1.setQuestion(answer.getQuestion());
        answer1.setStudent(answer.getStudent());
        answerRepository.save(answer1);
        questionService.addAnswerToQuestion(answer1.getQuestion().getId(),answer1);
    }


    @Override
    public Answer findByStudentIdAndQuestionId(Long studentId, Long questionId) {
        return answerRepository.findByStudentIdAndQuestionId(studentId, questionId);
    }

    @Override
    public Answer findById(Long id) {
        return answerRepository.findById(id).orElseThrow(() -> new RuntimeException("answer Not Found"));
    }

    @Override
    public void updateAnswer(Answer answer) {
        Answer answer1 = this.findById(answer.getId());
        answer1.setStudentAnswer(answer.getStudentAnswer());
        answer1.setStudentScore(answer.getStudentScore());
        answerRepository.save(answer1);
    }
}
