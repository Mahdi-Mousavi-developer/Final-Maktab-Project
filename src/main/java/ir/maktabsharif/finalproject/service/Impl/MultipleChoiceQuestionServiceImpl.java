package ir.maktabsharif.finalproject.service.Impl;

import ir.maktabsharif.finalproject.entity.MultipleChoiceQuestion;
import ir.maktabsharif.finalproject.entity.QuestionType;
import ir.maktabsharif.finalproject.repository.MultipleChoiceQuestionRepository;
import ir.maktabsharif.finalproject.service.ExamService;
import ir.maktabsharif.finalproject.service.MultipleChoiceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultipleChoiceQuestionServiceImpl implements MultipleChoiceService {
    private final MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;
    private final ExamService examService;


    @Autowired
    public MultipleChoiceQuestionServiceImpl(MultipleChoiceQuestionRepository multipleChoiceQuestionRepository, ExamService examService) {
        this.multipleChoiceQuestionRepository = multipleChoiceQuestionRepository;
        this.examService = examService;
    }

    @Override
    @Transactional
    public void Save(MultipleChoiceQuestion multipleChoiceQuestion) {
        MultipleChoiceQuestion multipleChoiceQuestion1 = new MultipleChoiceQuestion();
        multipleChoiceQuestion1.setQuestionScore(multipleChoiceQuestion.getQuestionScore());
        multipleChoiceQuestion1.setQuestionText(multipleChoiceQuestion.getQuestionText());
        multipleChoiceQuestion1.setQuestionType(QuestionType.MULTIPLE_CHOICE);
        multipleChoiceQuestion1.setTitle(multipleChoiceQuestion.getTitle());
        multipleChoiceQuestion1.setCorrectOption(multipleChoiceQuestion.getCorrectOption());
        multipleChoiceQuestion1.setChoices(multipleChoiceQuestion.getChoices());
        multipleChoiceQuestion1.setCourse(multipleChoiceQuestion.getCourse());
        multipleChoiceQuestion1.setTeacher(multipleChoiceQuestion.getTeacher());
        multipleChoiceQuestion1.setExam(multipleChoiceQuestion.getExam());
        multipleChoiceQuestion1.setQuestionType(QuestionType.MULTIPLE_CHOICE);
        multipleChoiceQuestion1.setAnswers(null);
        multipleChoiceQuestionRepository.save(multipleChoiceQuestion1);
        examService.addQuestionToExam(multipleChoiceQuestion1, multipleChoiceQuestion1.getExam());
    }

    @Override
    public MultipleChoiceQuestion findById(Long id) {
        return multipleChoiceQuestionRepository.findById(id).orElseThrow(() -> new RuntimeException("Course Not Found"));
    }

    @Override
    public void update(Long id, MultipleChoiceQuestion multipleChoiceQuestion) {
        MultipleChoiceQuestion multipleChoiceQuestion1 = this.findById(id);
        multipleChoiceQuestion1.setChoices(null);
        multipleChoiceQuestion1.setChoices(multipleChoiceQuestion.getChoices());
        multipleChoiceQuestion1.setTitle(multipleChoiceQuestion.getTitle());
        multipleChoiceQuestion1.setQuestionText(multipleChoiceQuestion.getQuestionText());
        multipleChoiceQuestion1.setQuestionScore(multipleChoiceQuestion.getQuestionScore());
        multipleChoiceQuestion1.setCorrectOption(multipleChoiceQuestion.getCorrectOption());
        multipleChoiceQuestionRepository.save(multipleChoiceQuestion1);
    }
    @Override
    public Boolean checkAnswer(Long questionId, String answer) {
            MultipleChoiceQuestion multipleChoiceQuestion = this.findById(questionId);
            if (answer.equalsIgnoreCase(multipleChoiceQuestion.getCorrectOption())){
                return true;
            }

        return false;
    }
}
