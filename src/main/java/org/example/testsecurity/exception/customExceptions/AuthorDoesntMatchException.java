package org.example.testsecurity.exception.customExceptions;

import lombok.Getter;
import org.example.testsecurity.exception.ErrorCode;

@Getter
public class AuthorDoesntMatchException extends RuntimeException{

    private ErrorCode errorCode;

    public AuthorDoesntMatchException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
