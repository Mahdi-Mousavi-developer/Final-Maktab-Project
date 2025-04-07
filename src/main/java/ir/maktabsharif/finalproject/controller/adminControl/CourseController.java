package ir.maktabsharif.finalproject.controller.adminControl;

import ir.maktabsharif.finalproject.entity.Course;
import ir.maktabsharif.finalproject.entity.Dto.CourseRequestDto;
import ir.maktabsharif.finalproject.entity.User;
import ir.maktabsharif.finalproject.exception.EntityNotFoundException;
import ir.maktabsharif.finalproject.service.CourseService;
import ir.maktabsharif.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;
    private final UserService userService;

    @Autowired
    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping
    public String courseList(Model model) {
        model.addAttribute("courseList", courseService.findAllCourse());
        return "/admin/courseList";
    }

    @GetMapping("/add")
    public String addCourse(Model model) throws EntityNotFoundException {
        model.addAttribute("courses", new Course());
        model.addAttribute("teachers", userService.findUserByRole("TEACHER"));
        return "/admin/new-course";
    }

    @PostMapping("/add")
    public String submitCourse(CourseRequestDto course) throws EntityNotFoundException {
        courseService.saveCourse(course);
        return "/admin/homepage";
    }
    @GetMapping("/{id}")
    public String showCourseDetail(Model model ,@PathVariable Long id ){
        Course course =courseService.findCourseById(id);
        model.addAttribute("course", course);
        return"/admin/courseDetail";
    }
    @PostMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) throws EntityNotFoundException {
        courseService.deleteCourseById(id);
        return "redirect:/course";
    }

    @GetMapping("/edit/{id}")
    public String showEditCourseForm(@PathVariable Long id, Model model) throws EntityNotFoundException {
        Course course = courseService.findCourseById(id);
        model.addAttribute("teachers", userService.findUserByRole("TEACHER"));
        model.addAttribute("course", course);
        return "/admin/editCourse";
    }

    @PostMapping("/{id}/edit")
    public String updateCourse(@PathVariable Long id, @ModelAttribute CourseRequestDto course) throws EntityNotFoundException {
        courseService.updateCourse(id, course);
        return "redirect:/course";
    }
    @GetMapping("/{id}/addStudent")
    public String showAddStudentForm(@PathVariable Long id, Model model) {
        Course course = courseService.findCourseById(id);
        List<User> availableStudents = userService.findStudentsNotInCourse(id);
        model.addAttribute("course", course);
        model.addAttribute("students", availableStudents);
        return "/admin/addStudentToCourse";
    }

    @PostMapping("/{id}/addStudent")
    public String addStudentToCourse(@PathVariable Long id, @RequestParam Long studentId) throws EntityNotFoundException {
        courseService.addStudentToCourse(id, studentId);
        return "redirect:/course";
    }


    @GetMapping("/{courseId}/remove-student")
    public String showRemoveStudentForm(@PathVariable Long courseId, Model model) {
        Course course = courseService.findCourseById(courseId);
        List<User> studentsInCourse = course.getStudents();
        model.addAttribute("course", course);
        model.addAttribute("students", studentsInCourse);
        return "/admin/removeStudentFromCourse";
    }
    @PostMapping("/{courseId}/remove-student")
    public String removeStudentFromCourse(
            @PathVariable Long courseId,
            @RequestParam Long studentId
    ) throws EntityNotFoundException {
        courseService.removeStudentFromCourse(courseId, studentId);
        return "redirect:/course";
    }
}