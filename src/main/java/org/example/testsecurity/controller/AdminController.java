package org.example.testsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.example.testsecurity.entity.User;
import org.example.testsecurity.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/admin")
    public String adminP(){
        return "admin";
    }

    @GetMapping("/admin/users")
    public String manageUsers(Model model){
        List<User> userList = userService.getUsers();
        model.addAttribute("userList", userList);
        return "manageUser";
    }
}
