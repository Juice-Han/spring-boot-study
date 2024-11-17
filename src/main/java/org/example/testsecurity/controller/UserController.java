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
            return "redirect:/articleList";
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
            return "redirect:/articleList";
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

            Map<String, String> validatorResult = userService.validateHandling(errors);
            for(String key: validatorResult.keySet()){
                model.addAttribute(key, validatorResult.get(key)); // validation 실패한 필드와 에러 메세지를 짝지어 모델에 추가
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
