package org.example.testsecurity.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.testsecurity.exception.customExceptions.ArticleDoesntExistException;
import org.example.testsecurity.exception.customExceptions.AuthorDoesntMatchException;
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

    @ExceptionHandler(ArticleDoesntExistException.class)
    public String handleArticleDoesntExistException(Model model, ArticleDoesntExistException e){
        log.error("handleArticleDoesntExistException", e);
        model.addAttribute("errorCode", e.getErrorCode());
        return "exception";
    }

    @ExceptionHandler(AuthorDoesntMatchException.class)
    public String handleAuthorDoesntMatchException(Model model, AuthorDoesntMatchException e){
        log.error("handleAuthorDoesntMatchException", e);
        model.addAttribute("errorCode", e.getErrorCode());
        return "exception";
    }
}
