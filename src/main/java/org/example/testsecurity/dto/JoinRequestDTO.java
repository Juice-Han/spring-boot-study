package org.example.testsecurity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinRequestDTO {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-z0-9]{5,20}$", message = "아이디는 영문 소문자와 숫자를 조합하여 5~20자로 작성하세요.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 영문 대소문자, 숫자, 특수문자를 조합하여 8~16자로 작성하세요")
    private String password;
}
