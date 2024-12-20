package org.example.testsecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleTitleAndContentDTO {
    private Long id;
    private String title;
    private String content;
    private String username;
}
