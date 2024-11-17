package org.example.testsecurity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_DOESNT_EXIST(404, "USER_DOESNT_EXIST"),
    USER_ALREADY_EXIST(404, "USER_ALREADY_EXIST")
    ;

    private int status;
    private String message;
}
