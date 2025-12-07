package com.project.expenseTracker.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.net.http.HttpClient;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse<T> implements ApiResponse{

    private final boolean success = false;

    private final int statusCode;

    private final String message;

    private final String details ;

    public ErrorResponse(String message, HttpStatus staus) {
        this.message = message;
        this.statusCode = staus.value();
        this.details = null;
    }

    public ErrorResponse(String message, HttpStatus status, String details) {
        this.message = message;
        this.statusCode = status.value();
        this.details = details;
    }


    // This stops "data" from appearing
    @JsonIgnore
    @Override
    public Object getData() {
        return details;
    }
}
