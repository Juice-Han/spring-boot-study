package org.example.testsecurity.service;

import lombok.RequiredArgsConstructor;
import org.example.testsecurity.dto.ArticleTitleAndContentDTO;
import org.example.testsecurity.entity.Article;
import org.example.testsecurity.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<ArticleTitleAndContentDTO> findAllArticleWithShortContent(){
        return articleRepository.findArticleWithShortContent();
    }
}
