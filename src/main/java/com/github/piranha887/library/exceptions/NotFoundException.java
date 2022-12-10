package com.github.piranha887.library.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AppException {
    public NotFoundException(String name, long id) {
        super(HttpStatus.NOT_FOUND, String.format("%s with id %d not found", name, id));
    }
}