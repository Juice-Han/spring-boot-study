package org.example.testsecurity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.testsecurity.dto.ArticleTitleAndContentDTO;
import org.example.testsecurity.dto.ArticleWithUsernameDTO;
import org.example.testsecurity.dto.WriteArticleRequestDTO;
import org.example.testsecurity.entity.Article;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String getArticle(@PathVariable("id") Long id, Model model) {
        ArticleWithUsernameDTO articleDTO = articleService.findArticleById(id);
        model.addAttribute("article", articleDTO);
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

}
