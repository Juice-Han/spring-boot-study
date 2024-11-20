package org.example.testsecurity.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.testsecurity.dto.JoinRequestDTO;
import org.example.testsecurity.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginP(HttpServletRequest request){

        if(userService.isAuthenticated()){
            return "redirect:/articles";
        }

        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/";
    }

    @GetMapping("/join")
    public String joinP(){

        if(userService.isAuthenticated()){
            return "redirect:/articles";
        }

        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(@Valid JoinRequestDTO joinRequestDTO, Errors errors, Model model){

        if(userService.isAuthenticated()){
            return "redirect:/";
        }

        Map<String ,String> joinInfo = new HashMap<>();
        joinInfo.put("username", joinRequestDTO.getUsername());
        joinInfo.put("password", joinRequestDTO.getPassword());

        if(errors.hasErrors()){

            model.addAttribute("joinInfo", joinInfo); // 사용자 입력값 기억

            for(FieldError error : errors.getFieldErrors()){
                String validKeyName = String.format("validMessage_%s", error.getField());
                model.addAttribute(validKeyName, error.getDefaultMessage());
            }

            return "join";
        }

        boolean isCreated = userService.joinProcess(joinRequestDTO); // 중복된 아이디

        if(!isCreated){
            model.addAttribute("joinInfo", joinInfo);
            model.addAttribute("duplicateId",true);
            return "join";
        }

        return "redirect:/login";
    }
}
