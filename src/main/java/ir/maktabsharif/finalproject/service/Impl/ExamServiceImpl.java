package ir.maktabsharif.finalproject.service.Impl;

import ir.maktabsharif.finalproject.entity.Course;
import ir.maktabsharif.finalproject.entity.Dto.ExamRequestDto;
import ir.maktabsharif.finalproject.entity.Exam;
import ir.maktabsharif.finalproject.entity.Question;
import ir.maktabsharif.finalproject.exception.EntityNotFoundException;
import ir.maktabsharif.finalproject.repository.ExamRepository;
import ir.maktabsharif.finalproject.service.ExamService;
import ir.maktabsharif.finalproject.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final QuestionService questionService;

    @Autowired
    public ExamServiceImpl(ExamRepository examRepository, QuestionService questionService) {
        this.examRepository = examRepository;
        this.questionService = questionService;
    }

//    public Exam mapDtoToEntity(ExamRequestDto examRequestDto) {
//        return Exam.builder()
//                .duration(examRequestDto.getDuration())
//                .name(examRequestDto.getName())
//                .description(examRequestDto.getDescription())
//                .course(examRequestDto.getCourse())
//                .teacher(examRequestDto.getCourse().getTeacher())
//                .build();
//    }

    @Override
    public void saveExam(ExamRequestDto examRequestDto) {
        Exam exam = new Exam();
        exam.setName(examRequestDto.getName());
        exam.setDescription(examRequestDto.getDescription());
        exam.setDuration(examRequestDto.getDuration());
        exam.setCourse(examRequestDto.getCourse());
        exam.setTeacher(examRequestDto.getTeacher());
        exam.setStartDate(examRequestDto.getStartDate());
        exam.setEndDate(examRequestDto.getEndDate());
        examRepository.save(exam);
    }

    @Override
    public void updateExam(Long id, ExamRequestDto examRequestDto) throws EntityNotFoundException {
        Exam exam = examRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("exam not found"));
        exam.setName(examRequestDto.getName());
        exam.setDescription(examRequestDto.getDescription());
        exam.setDuration(examRequestDto.getDuration());
        exam.setStartDate(examRequestDto.getStartDate());
        exam.setEndDate(examRequestDto.getEndDate());
        examRepository.save(exam);
    }

    @Override
    public void deleteExamById(Long id) throws EntityNotFoundException {
        Exam exam = examRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("exam not found"));
        exam.setCourse(null);
        exam.setTeacher(null);
        exam.setQuestions(null);
        examRepository.delete(exam);
    }

    @Override
    public List<Exam> getExamList() {
        return examRepository.findAll();
    }

    @Override
    public Exam getExamById(Long id) throws EntityNotFoundException {
        return examRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("exam not found"));
    }

    @Override
    public List<Exam> findExamByCourse(Course course) {
        return examRepository.findByCourse(course);
    }

    public void updateExamWithoutDto(Exam exam) {
        examRepository.save(exam);
    }

    public void addQuestionToExam(Question question, Exam exam) {
        exam.getQuestions().add(question);
        question.setExam(exam);
        examRepository.save(exam);
    }

    @Override
    public List<Exam> findExamsByCourseAndTeacher(Long teacherId, Course course) {
        return List.of();
    }

    @Override
    public int setScore(Long examId) throws EntityNotFoundException {
    List<Question> questionList = questionService.findByExamId(examId);
    int score = 0;
    for(Question question : questionList){
        score += question.getQuestionScore();
    }
    Exam exam =this.getExamById(examId);
    exam.setScore(score);
    examRepository.save(exam);
        return score;
    }
}
