package org.example.testsecurity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.testsecurity.dto.*;
import org.example.testsecurity.service.ArticleService;
import org.example.testsecurity.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final UserService userService;
    private final ArticleService articleService;

    @GetMapping("/articles")
    public String articleList(Model model) {
        List<ArticleTitleAndContentDTO> articleList = articleService.findAllArticleWithShortContent();
        model.addAttribute("articleList", articleList);
        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable("id") Long id, Model model, Authentication authentication) {
        ArticleWithUsernameDTO articleDTO = articleService.findArticleById(id);
        model.addAttribute("article", articleDTO);
        if(articleDTO.getUsername().equals(authentication.getName())){
            return "articleDetailCanUpdateDelete";
        }

        return "articleDetail";
    }

    @GetMapping("/articles/write")
    public String writeArticle(Model model) {
        return "writeArticle";
    }

    @PostMapping("/articles")
    public String writeProc(@Valid WriteArticleRequestDTO requestDTO, Errors errors, Model model, Authentication authentication) {
        if (errors.hasErrors()) {
            model.addAttribute("writeInfo", requestDTO);

            for(FieldError error : errors.getFieldErrors()){
                String validKeyName = String.format("validMessage_%s", error.getField());
                model.addAttribute(validKeyName, error.getDefaultMessage());
            }

            return "writeArticle";
        }

        articleService.save(requestDTO, authentication.getName());

        return "redirect:/articles";
    }

    @GetMapping("/articles/{id}/update")
    public String updateP(@PathVariable("id") Long id, Model model, Authentication authentication) {
        ArticleDTO articleDTO = articleService.findArticleByIdAndCheckUser(id, authentication.getName());

        model.addAttribute("updateInfo", articleDTO);

        return "updateArticle";
    }

    @PostMapping("/articles/{id}/update")
    public String updateArticle(@PathVariable("id") Long id, @Valid UpdateArticleRequestDTO requestDTO,
                                Errors errors, Model model, Authentication authentication){
        if (errors.hasErrors()) {
            model.addAttribute("updateInfo", requestDTO);

            for(FieldError error : errors.getFieldErrors()){
                String validKeyName = String.format("validMessage_%s", error.getField());
                model.addAttribute(validKeyName, error.getDefaultMessage());
            }

            return "updateArticle";
        }

        articleService.update(id, requestDTO, authentication.getName());

        return "redirect:/articles/"+id;
    }

}
