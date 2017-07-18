package com.assignment.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Created by manish on 18/07/17.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AssignmentException extends RuntimeException {

    String message;
    HttpStatus status;
    Throwable throwable;


    public AssignmentException (HttpStatus status) {
        super();
        this.status = status;
    }

    public AssignmentException (String message, HttpStatus status) {
        super();
        this.status = status;
        this.message = message;
    }


    public AssignmentException (Throwable throwable, HttpStatus status) {
        super();
        this.status = status;
        this.throwable = throwable;
    }
}
