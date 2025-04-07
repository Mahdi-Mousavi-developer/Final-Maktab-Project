package ir.maktabsharif.finalproject.controller.adminControl;

import ir.maktabsharif.finalproject.exception.EntityNotFoundException;
import ir.maktabsharif.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UsersController {
    private final UserService userService;
    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/pending")
    public String pending(Model model) {
        model.addAttribute("users" , userService.findAllUserPendingRegistrationStatus());
        return "/admin/pending";
    }
    @GetMapping("/approve/{id}")
    public String approve(@PathVariable Long id) throws EntityNotFoundException {
        userService.approveUser(id);
        return "redirect:/pending";
    }

}
