package ir.maktabsharif.finalproject.service.Impl;

import ir.maktabsharif.finalproject.entity.*;
import ir.maktabsharif.finalproject.repository.QuestionRepository;
import ir.maktabsharif.finalproject.service.QuestionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Question> findByCourseAndTeacherId(Long courseId, Long teacherId, Long examId) {
        List<Question> questions = questionRepository.findByCourseIdAndTeacherIdAndExamIdNot(courseId, teacherId, examId);
        return questions;
    }

    @Override
    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Course Not Found"));
    }

    @Override
    public List<Question> findByExamId(Long examId) {
        return questionRepository.findByExamId(examId);
    }

    @Override
    public void deleteById(Long id) {
        Question question = this.findById(id);
        question.setExam(null);
        question.setTeacher(null);
        question.setCourse(null);
        questionRepository.delete(question);
    }

    @Override
    @Transactional
    public void updateQuestion(Long id, Question updatedQuestion) {
        Question question = this.findById(id);
        // به‌روزرسانی فیلدهای مشترک
        question.setTitle(updatedQuestion.getTitle());
        question.setQuestionText(updatedQuestion.getQuestionText());
        question.setQuestionScore(updatedQuestion.getQuestionScore());

        if (question instanceof DescriptiveQuestion && updatedQuestion instanceof DescriptiveQuestion) {
            // به‌روزرسانی سوال تشریحی
            ((DescriptiveQuestion) question).setAnswer(((DescriptiveQuestion) updatedQuestion).getAnswer());
        } else if (question instanceof MultipleChoiceQuestion && updatedQuestion instanceof MultipleChoiceQuestion) {
            // به‌روزرسانی سوال چندگزینه‌ای
            MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) question;
            MultipleChoiceQuestion updatedMultipleChoiceQuestion = (MultipleChoiceQuestion) updatedQuestion;

            multipleChoiceQuestion.setCorrectOption(updatedMultipleChoiceQuestion.getCorrectOption());

            // به‌روزرسانی Choices
            multipleChoiceQuestion.getChoices().clear(); // پاک کردن Choices قدیمی
            multipleChoiceQuestion.getChoices().addAll(updatedMultipleChoiceQuestion.getChoices()); // اضافه کردن Choices جدید
        }

        questionRepository.save(question); // ذخیره تغییرات
    }

    @Override
    public void addAnswerToQuestion(Long questionId, Answer answer) {
        Question question = this.findById(questionId);
        List<Answer> answers = question.getAnswers();
        answers.add(answer);
        question.setAnswers(answers);

    }
}
