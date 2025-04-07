package ir.maktabsharif.finalproject.controller.teacherControl;

import ir.maktabsharif.finalproject.entity.*;
import ir.maktabsharif.finalproject.exception.EntityNotFoundException;
import ir.maktabsharif.finalproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/teacher-course")
public class TeacherControl {
    private final CourseService courseService;
    private final UserService userService;
    private final ExamService examService;
    private final ExamStudentService examStudentService;
    private final AnswerService answerService;
    private final QuestionService questionService;

    @Autowired
    public TeacherControl(CourseService courseService, UserService userService, ExamService examService, ExamStudentService examStudentService, AnswerService answerService, QuestionService questionService) {
        this.courseService = courseService;
        this.userService = userService;
        this.examService = examService;
        this.examStudentService = examStudentService;
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @GetMapping("/{id}")
    public String courseListForTeacher(@PathVariable Long id, Model model) throws EntityNotFoundException {
        User user = userService.findUserById(id);
        List<Course> courseList = courseService.findCourseByTeacherId(user);
        model.addAttribute("courseList", courseList);
        return "/teacher/courseList";
    }

    @GetMapping("/{id}/detail")
    public String showCourse(@PathVariable Long id, Model model) throws EntityNotFoundException {
        Course course = courseService.findCourseById(id);
        model.addAttribute("course", course);
        return "/teacher/courseDetails";
    }

    @GetMapping("/Grading/{id}")
    public String courseListForTeacherGrading(@PathVariable Long id, Model model) throws EntityNotFoundException {
        User user = userService.findUserById(id);
        List<Course> courseList = courseService.findCourseByTeacherId(user);
        model.addAttribute("courseList", courseList);
        return "/teacher/grading/courseListForGrading";
    }

    @GetMapping("/Grading/{id}/detail")
    public String showCourseForGrading(@PathVariable Long id, Model model) throws EntityNotFoundException {
        Course course = courseService.findCourseById(id);
        model.addAttribute("exams", course.getExamList());
        model.addAttribute("course", course);
        return "/teacher/grading/courseDetails";
    }

    @GetMapping("/grading/exam/{examId}")
    public String ShowExamStudent(@PathVariable Long examId, Model model) throws EntityNotFoundException {
        Exam exam = examService.getExamById(examId);
        List<ExamStudent> examStudents = examStudentService.findAllByExamId(examId);
        model.addAttribute("exam", exam);
        model.addAttribute("examStudents", examStudents);
        model.addAttribute("CountOfStudent", examStudents.size());
        return "/teacher/grading/examStudentList";
    }

    @GetMapping("/grading/teacher-grading-descriptiveQuestion/{examId}/{studentId}")
    public String ShowGradingPage(
            @PathVariable Long examId,
            @PathVariable Long studentId,
            Model model
    ) throws EntityNotFoundException {
        Exam exam = examService.getExamById(examId);
        ExamStudent examStudent = examStudentService.findByStudentIdAndExamId(studentId, examId);
        List<Answer> answers = new ArrayList<>();
        List<Question> questions = exam.getQuestions();
        for (Question question : questions) {
            if (question.getQuestionType().equals(QuestionType.DESCRIPTIVE)) {
                Optional<Answer> answer = Optional.ofNullable(answerService.findByStudentIdAndQuestionId(studentId, question.getId()));
                if (answer.isPresent()) {
                    if (answer.get().getStudentScore() == 0) {
                        answers.add(answer.get());
                    }
                }

            }
        }
        model.addAttribute("answers", answers);
        model.addAttribute("exam", exam);
        model.addAttribute("examStudent", examStudent);

        return "/teacher/grading/teacherGrading";
    }

    @PostMapping("/grading/teacher-grading-descriptiveQuestion/{answer}/{examId}/submit")
    @PreAuthorize("hasAnyAuthority('TEACHER') and authentication.principal.id == @customUserDetailsService.currentUserId()")

    public String submitScore(@PathVariable Long examId,
                              @PathVariable Long answer,
                              @RequestParam int grade,
                            RedirectAttributes redirectAttributes) {
        Answer answer1 = answerService.findById(answer);
        answer1.setStudentScore(grade);
        answerService.updateAnswer(answer1);
        Long studentId = answer1.getStudent().getId();
        ExamStudent examStudent = examStudentService.findByStudentIdAndExamId(studentId,examId);
        Integer totalScore = examStudentService.getStudentFinalScore(examStudent);
        examStudent.setFinalScore(totalScore);
        examStudentService.updateFinalScore(totalScore ,examStudent);
        redirectAttributes.addAttribute("examId", examId);
        redirectAttributes.addAttribute("studentId",studentId);

        return "redirect:/teacher-course/grading/teacher-grading-descriptiveQuestion/{examId}/{studentId}";

    }

}
