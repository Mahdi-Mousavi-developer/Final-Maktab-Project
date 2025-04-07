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
@RequestMapping("/teachers")
public class TeacherController {
    private final UserService userService;

    @Autowired
    public TeacherController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String teacherPage(Model model) throws EntityNotFoundException {
        List<User> teachers = userService.findUserByRole("TEACHER");
        model.addAttribute("teachers", teachers);
        return "/admin/teachers/list";
    }

    @GetMapping("/edit")
    public String ShowEditeForm(@RequestParam("id") Long id, Model model) throws EntityNotFoundException {
        User teacher = userService.findUserById(id);
        model.addAttribute("teacher", teacher);
        return "/admin/teachers/edit";
    }

    @PostMapping("/edit")
    public String updateTeacher(
            @RequestParam("id") Long id,
            @ModelAttribute("teacherDTO") WebappUserRequestDto teacherDTO) throws EntityNotFoundException {
        userService.updateWithoutChangingPassword(id, teacherDTO);
        return "redirect:/homepage";
    }

    @PostMapping("/delete")
    public String deleteTeacher(@RequestParam("id") Long id, Model model) {
        List<String> error = null;
        try {
            userService.deleteUser(id);
        } catch (EntityNotFoundException e) {
         error.add(e.getMessage()) ;
        }
        model.addAttribute("error" , error);
        return "redirect:/teachers";
    }


}


