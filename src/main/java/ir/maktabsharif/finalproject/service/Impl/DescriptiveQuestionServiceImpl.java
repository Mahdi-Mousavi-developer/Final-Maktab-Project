package ir.maktabsharif.finalproject.service.Impl;

import ir.maktabsharif.finalproject.entity.DescriptiveQuestion;
import ir.maktabsharif.finalproject.entity.QuestionType;
import ir.maktabsharif.finalproject.repository.DescriptiveQuestionRepository;
import ir.maktabsharif.finalproject.service.DescriptiveQuestionService;
import ir.maktabsharif.finalproject.service.ExamService;
import org.springframework.stereotype.Service;

@Service
public class DescriptiveQuestionServiceImpl implements DescriptiveQuestionService {
    private final DescriptiveQuestionRepository descriptiveQuestionRepository;
    private final ExamService examService;

    public DescriptiveQuestionServiceImpl(DescriptiveQuestionRepository descriptiveQuestionRepository, ExamService examService) {
        this.descriptiveQuestionRepository = descriptiveQuestionRepository;
        this.examService = examService;
    }

    @Override
    public void save(DescriptiveQuestion question) {
        DescriptiveQuestion descriptiveQuestion = new DescriptiveQuestion();
        descriptiveQuestion.setQuestionScore(question.getQuestionScore());
        descriptiveQuestion.setQuestionText(question.getQuestionText());
        descriptiveQuestion.setQuestionType(QuestionType.DESCRIPTIVE);
        descriptiveQuestion.setExam(question.getExam());
        descriptiveQuestion.setCourse(question.getCourse());
        descriptiveQuestion.setTitle(question.getTitle());
        descriptiveQuestion.setTeacher(question.getTeacher());
        descriptiveQuestion.setAnswers(null);
        descriptiveQuestionRepository.save(descriptiveQuestion);
        examService.addQuestionToExam(descriptiveQuestion , question.getExam());
    }

    @Override
    public DescriptiveQuestion findById(Long id) {
        return descriptiveQuestionRepository.findById(id).orElseThrow(() -> new RuntimeException("Course Not Found"));
    }
}
