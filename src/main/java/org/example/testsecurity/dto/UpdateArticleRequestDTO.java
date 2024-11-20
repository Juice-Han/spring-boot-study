package org.example.testsecurity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateArticleRequestDTO {

    @NotBlank(message = "제목은 필수 입력사항입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력사항입니다.")
    private String content;
}
