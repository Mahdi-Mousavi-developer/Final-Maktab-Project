package ir.maktabsharif.finalproject.controller.adminControl;

import ir.maktabsharif.finalproject.entity.Dto.WebappUserRequestDto;
import ir.maktabsharif.finalproject.entity.User;
import ir.maktabsharif.finalproject.exception.EntityNotFoundException;
import ir.maktabsharif.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final UserService userService;
    @Autowired
    public StudentController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String studentPage(Model model) throws EntityNotFoundException {
        List<User> teachers = userService.findUserByRole("STUDENT");
        model.addAttribute("teachers", teachers);
        return "/admin/students/list";
    }

    @GetMapping("/edit")
    public String ShowEditeForm(@RequestParam("id") Long id, Model model) throws EntityNotFoundException {
        User teacher = userService.findUserById(id);
        model.addAttribute("teacher", teacher);
        return "/admin/students/edit";
    }

    @PostMapping("/edit")
    public String updateStudents(
            @RequestParam("id") Long id,
            @ModelAttribute("teacherDTO") WebappUserRequestDto teacherDTO) throws EntityNotFoundException {
        userService.updateWithoutChangingPassword(id, teacherDTO);
        return "redirect:/homepage";
    }
    @PostMapping("/delete")
    public String deleteStudents(@RequestParam("id") Long id) throws EntityNotFoundException {
        userService.deleteUser(id);
        return "redirect:/students";
    }
}
