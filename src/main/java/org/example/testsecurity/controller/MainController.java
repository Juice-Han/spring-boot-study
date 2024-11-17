package org.example.testsecurity.controller;

import org.example.testsecurity.exception.ErrorCode;
import org.example.testsecurity.exception.customExceptions.UserDoesntExistException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainP(){
        return "main";
    }

    @GetMapping("/errorExample")
    public String errorExample(){
        throw new UserDoesntExistException("User doesnt exist", ErrorCode.USER_DOESNT_EXIST);
    }
}
