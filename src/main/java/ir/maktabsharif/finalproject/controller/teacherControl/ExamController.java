package ir.maktabsharif.finalproject.controller.teacherControl;

import ir.maktabsharif.finalproject.entity.Course;
import ir.maktabsharif.finalproject.entity.Dto.ExamRequestDto;
import ir.maktabsharif.finalproject.entity.Exam;
import ir.maktabsharif.finalproject.exception.EntityNotFoundException;
import ir.maktabsharif.finalproject.service.CourseService;
import ir.maktabsharif.finalproject.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teacher-exam")
public class ExamController {
    private final CourseService courseService;
    private final ExamService examService;

    @Autowired
    public ExamController(CourseService courseService, ExamService examService) {
        this.courseService = courseService;
        this.examService = examService;
    }

    @GetMapping("/{id}")
    public String showExamPage(@PathVariable Long id, Model model) throws EntityNotFoundException {
        Course course = courseService.findCourseById(id);
        List<Exam> exams = examService.findExamByCourse(course);
        for (Exam exam : exams) {
            examService.setScore(exam.getId());
        }
        model.addAttribute("course", course);
        model.addAttribute("exams", exams);
        return "/teacher/examPage";
    }

    @GetMapping("/create/{id}")
    public String showCreateExamPage(@PathVariable Long id, Model model) throws EntityNotFoundException {
        Course course = courseService.findCourseById(id);
        Exam exam = new Exam();

        model.addAttribute("course", course);
        model.addAttribute("exam", exam);
        return "/teacher/examCreate";
    }

    @PostMapping("/create/{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER') and authentication.principal.id == @customUserDetailsService.currentUserId()")
    public String createExam(@ModelAttribute ExamRequestDto examRequestDto, @PathVariable Long id) {
        Course course = courseService.findCourseById(id);
        examRequestDto.setTeacher(course.getTeacher());
        examRequestDto.setCourse(course);
        examService.saveExam(examRequestDto);
        return "redirect:/homepage";
    }

    @GetMapping("/edit/{id}")
    public String ShowEditeForm(@PathVariable Long id, Model model) throws EntityNotFoundException {
        Exam exam = examService.getExamById(id);
        model.addAttribute("exam", exam);
        return "/teacher/editExam";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAnyAuthority('TEACHER') and authentication.principal.id == @customUserDetailsService.currentUserId()")
    public String updateExam(
            @RequestParam("id") Long id,
            @ModelAttribute("examRequestDto") ExamRequestDto examRequestDto) throws EntityNotFoundException {
        examService.updateExam(id, examRequestDto);
        return "redirect:/homepage";
    }

    @PostMapping("/delete")
    public String deleteExam(@RequestParam("id") Long id) throws EntityNotFoundException {
        examService.deleteExamById(id);
        return "redirect:/homepage";
    }
}
