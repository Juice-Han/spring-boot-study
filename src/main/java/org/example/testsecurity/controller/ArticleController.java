package org.example.testsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.example.testsecurity.dto.ArticleTitleAndContentDTO;
import org.example.testsecurity.dto.ArticleWithUsernameDTO;
import org.example.testsecurity.entity.Article;
import org.example.testsecurity.service.ArticleService;
import org.example.testsecurity.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final UserService userService;
    private final ArticleService articleService;

    @GetMapping("/articleList")
    public String articleList(Model model) {
        List<ArticleTitleAndContentDTO> articleList = articleService.findAllArticleWithShortContent();
        model.addAttribute("articleList", articleList);
        return "articleList";
    }

    @GetMapping("/articleList/{id}")
    public String getArticle(@PathVariable("id") Long id, Model model) {
        ArticleWithUsernameDTO articleDTO = articleService.findArticleById(id);
        model.addAttribute("article", articleDTO);
        return "articleDetail";
    }

}
