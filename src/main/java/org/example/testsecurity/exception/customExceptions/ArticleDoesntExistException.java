package org.example.testsecurity.exception.customExceptions;

import lombok.Getter;
import org.example.testsecurity.exception.ErrorCode;

@Getter
public class ArticleDoesntExistException extends RuntimeException{

    private ErrorCode errorCode;

    public ArticleDoesntExistException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
