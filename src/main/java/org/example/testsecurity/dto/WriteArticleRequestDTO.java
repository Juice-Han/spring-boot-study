package org.example.testsecurity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.testsecurity.entity.Article;
import org.example.testsecurity.entity.User;

@Getter
@Setter
@NoArgsConstructor
public class WriteArticleRequestDTO {

    @NotBlank(message = "제목은 필수 입력사항입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력사항입니다.")
    private String content;

    public Article toEntity(User user){
        return Article.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}
