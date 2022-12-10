package com.github.piranha887.library.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AppException extends RuntimeException {
    protected final HttpStatus status;
    protected final String Message;

    public AppException(HttpStatus status, String message) {
        this.status = status;
        Message = message;
    }
}
