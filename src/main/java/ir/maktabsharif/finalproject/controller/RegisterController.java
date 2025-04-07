package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.entity.Dto.WebappUserRequestDto;
import ir.maktabsharif.finalproject.entity.User;
import ir.maktabsharif.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping
    public String createWebappUser(@ModelAttribute WebappUserRequestDto webappUserRequestDto) {
        userService.createUser(webappUserRequestDto);
        return "redirect:/login";
    }
}
