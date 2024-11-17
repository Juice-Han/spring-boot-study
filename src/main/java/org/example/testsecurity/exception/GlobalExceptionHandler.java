package org.example.testsecurity.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.testsecurity.exception.customExceptions.UserDoesntExistException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserDoesntExistException.class)
    public String handleUserDoesntExistException(Model model, UserDoesntExistException e){
        log.error("handleUserDoesntExistException", e);
        model.addAttribute("errorCode", e.getErrorCode());
        return "exception";
    }
}
