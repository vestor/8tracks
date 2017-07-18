package com.assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Created by manish on 18/07/17.
 */
@Data
public class ErrorResponse {
    @JsonIgnore
    Throwable throwable;
    String details;

    public ErrorResponse(Throwable t) {
        this.throwable = t;
        this.details = t.getMessage();
    }

    public ErrorResponse(String details, Throwable t) {
        this.throwable = t;
        this.details = details;
    }
}
