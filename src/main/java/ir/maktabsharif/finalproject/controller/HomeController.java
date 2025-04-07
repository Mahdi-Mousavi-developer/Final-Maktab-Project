package ir.maktabsharif.finalproject.controller;

import ir.maktabsharif.finalproject.entity.User;
import ir.maktabsharif.finalproject.exception.EntityNotFoundException;
import ir.maktabsharif.finalproject.service.UserService;
import ir.maktabsharif.finalproject.service.password.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/homepage")
public class HomeController {
    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String SendEachRoleToItsOwnPage(@AuthenticationPrincipal UserDetails userDetails, Model model) throws EntityNotFoundException {

        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
        if (customUserDetails.getAuthoritiesRegisterStatus().stream().anyMatch(a -> a.getAuthority().equals("APPROVAL"))) {
            if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
                return "/admin/homepage";
            } else if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("TEACHER"))) {
//                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                Long id = ((CustomUserDetails) userDetails).getWebappUser().getId();
//                model.addAttribute("username", auth.getName());
                model.addAttribute("id", id);
                return "/teacher/homepage";
            } else if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("STUDENT"))) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                model.addAttribute("username", auth.getName());
                Long id = ((CustomUserDetails) userDetails).getWebappUser().getId();
                model.addAttribute("studentId", id);
                User usere = userService.findUserById(id);
                model.addAttribute("courses",usere.getCoursesForStudent());
                return "/student/homepage";
            }
        }
        model.addAttribute("error", "You have not yet been approved by the administrator.");
        return "login";
    }
}
