package org.example.testsecurity.service;

import lombok.RequiredArgsConstructor;
import org.example.testsecurity.dto.*;
import org.example.testsecurity.entity.Article;
import org.example.testsecurity.entity.User;
import org.example.testsecurity.exception.ErrorCode;
import org.example.testsecurity.exception.customExceptions.ArticleDoesntExistException;
import org.example.testsecurity.exception.customExceptions.AuthorDoesntMatchException;
import org.example.testsecurity.exception.customExceptions.UserDoesntExistException;
import org.example.testsecurity.repository.ArticleRepository;
import org.example.testsecurity.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public List<ArticleTitleAndContentDTO> findAllArticleWithShortContent(){
        return articleRepository.findArticleWithShortContent();
    }

    public ArticleWithUsernameDTO findArticleById(Long id){
        return articleRepository.findArticleByIdWithUsername(id)
                .orElseThrow(()-> new ArticleDoesntExistException("Article doesnt exist", ErrorCode.ARTICLE_DOESNT_EXIST));
    }

    @Transactional
    public void save(WriteArticleRequestDTO requestDTO, String username){
        User user = userRepository.findByUsername(username)
                        .orElseThrow(()-> new UserDoesntExistException("User doesnt exist", ErrorCode.USER_DOESNT_EXIST));

        articleRepository.save(requestDTO.toEntity(user));
    }

    public ArticleDTO findArticleByIdAndCheckUser(Long articleId, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UserDoesntExistException("User doesnt exist", ErrorCode.USER_DOESNT_EXIST));

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleDoesntExistException("Article doesnt exist", ErrorCode.ARTICLE_DOESNT_EXIST));

        if(user != article.getUser()){
            throw new AuthorDoesntMatchException("Author doesnt match", ErrorCode.AUTHOR_DOESNT_MATCH);
        }

        return ArticleDTO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .build();
    }

    @Transactional
    public void update(Long id, UpdateArticleRequestDTO requestDTO, String username) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleDoesntExistException("Article doesnt exist", ErrorCode.ARTICLE_DOESNT_EXIST));

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UserDoesntExistException("User doesnt exist", ErrorCode.USER_DOESNT_EXIST));

        if(article.getUser() != user){
            throw new AuthorDoesntMatchException("Author doesnt match", ErrorCode.AUTHOR_DOESNT_MATCH);
        }

        article.updateTitle(requestDTO.getTitle());
        article.updateContent(requestDTO.getContent());
        articleRepository.save(article);
    }

    public void delete(Long id, String username){
        Article article = articleRepository.findById(id)
                        .orElseThrow(()-> new ArticleDoesntExistException("Article doesnt exist", ErrorCode.ARTICLE_DOESNT_EXIST));
        if(!article.getUser().getUsername().equals(username)) return;

        articleRepository.delete(article);
    }

}
