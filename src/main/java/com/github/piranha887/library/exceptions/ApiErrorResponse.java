package com.github.piranha887.library.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {
    private int status;
    private String message;

    public ApiErrorResponse(AppException e) {
        this.status = e.getStatus().value();
        this.message = e.getMessage();
    }

}
