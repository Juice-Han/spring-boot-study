package org.example.testsecurity.service;

import lombok.RequiredArgsConstructor;
import org.example.testsecurity.dto.ArticleTitleAndContentDTO;
import org.example.testsecurity.dto.ArticleWithUsernameDTO;
import org.example.testsecurity.dto.WriteArticleRequestDTO;
import org.example.testsecurity.entity.Article;
import org.example.testsecurity.entity.User;
import org.example.testsecurity.exception.ErrorCode;
import org.example.testsecurity.exception.customExceptions.ArticleDoesntExistException;
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

}
