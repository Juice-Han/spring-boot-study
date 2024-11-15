package org.example.testsecurity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.testsecurity.dto.JoinRequestDTO;
import org.example.testsecurity.service.JoinService;
import org.example.testsecurity.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final JoinService joinService;
    private final UserService userService;

    @GetMapping("/join")
    public String joinP(){
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(@Valid JoinRequestDTO joinRequestDTO, Errors errors, Model model){

        if(errors.hasErrors()){

            model.addAttribute("userInfo", joinRequestDTO); // 사용자 입력값 기억

            Map<String, String> validatorResult = userService.validateHandling(errors);
            for(String key: validatorResult.keySet()){
                model.addAttribute(key, validatorResult.get(key)); // validation 실패한 필드와 에러 메세지를 짝지어 모델에 추가
            }

            return "join";
        }

        joinService.joinProcess(joinRequestDTO);

        return "redirect:/login";
    }
}
