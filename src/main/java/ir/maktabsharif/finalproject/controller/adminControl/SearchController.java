package ir.maktabsharif.finalproject.controller.adminControl;

import ir.maktabsharif.finalproject.entity.User;
import ir.maktabsharif.finalproject.exception.EntityNotFoundException;
import ir.maktabsharif.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {
    private final UserService userService;

    @Autowired
    public SearchController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showSearchPage(
            @RequestParam(required = false) String searchField,
            @RequestParam(required = false) String searchValue,
            Model model
    ) throws EntityNotFoundException {
        List<User> userList = List.of();

        if (searchField != null && searchValue != null) {
            switch (searchField) {
                case "role":
                    userList = userService.findUserByRole(searchValue);
                    break;
                case "nationalCode":
                    userList = userService.findUserByNationalCode(searchValue);
                    break;
                case "username":
                    userList = userService.findUserByUsernameForSearch(searchValue);
                    break;
                case "firstName":
                    userList = userService.findUserByLastFirstName(searchValue);
                    break;
                case "lastName":
                    userList = userService.findUserByLastName(searchValue);
                    break;
                default:
                    // هیچ عملیاتی انجام نده
                    break;
            }
        }

        model.addAttribute("users", userList);
        return "/admin/search";
    }
}
