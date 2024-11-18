package org.example.testsecurity.repository;

import org.example.testsecurity.dto.ArticleTitleAndContentDTO;
import org.example.testsecurity.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(value = "select new org.example.testsecurity.dto.ArticleTitleAndContentDTO(ac.title, substring(ac.content,1,100)) from Article ac")
    List<ArticleTitleAndContentDTO> findArticleWithShortContent();
}
