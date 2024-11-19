package org.example.testsecurity.repository;

import org.example.testsecurity.dto.ArticleTitleAndContentDTO;
import org.example.testsecurity.dto.ArticleWithUsernameDTO;
import org.example.testsecurity.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(value = "select new org.example.testsecurity.dto.ArticleTitleAndContentDTO(" +
            "ac.id, ac.title, substring(ac.content,1,100), ac.user.username) from Article ac")
    List<ArticleTitleAndContentDTO> findArticleWithShortContent();

    @Query(value = "select new org.example.testsecurity.dto.ArticleWithUsernameDTO(" +
            "ac.title, ac.content, ac.user.username) from Article ac where ac.id = :articleId")
    Optional<ArticleWithUsernameDTO> findArticleByIdWithUsername(@Param("articleId") Long articleId);
}
