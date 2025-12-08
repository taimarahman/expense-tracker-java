package com.project.expenseTracker.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SuccessResponse<T> implements ApiResponse {

    private final boolean success = true;
    private final int statusCode;
    private final String message;
    private final T data;

    private SuccessResponse(T data, String message, HttpStatus status) {
        this.data = data;
        this.message = message;
        this.statusCode = status.value();
    }

    private SuccessResponse(String message, HttpStatus status) {
        this.data = null;
        this.message = message;
        this.statusCode = status.value();
    }

    private SuccessResponse(T data, String message) {
        this.data = data;
        this.message = message;
        this.statusCode = HttpStatus.FOUND.value();
    }

    private SuccessResponse(String message) {
        this.data = null;
        this.message = message;
        this.statusCode = HttpStatus.OK.value();
    }

    public static <T> SuccessResponse<T> of(T data, String message, HttpStatus status) {
        return new SuccessResponse<>(data, message, status);
    }

    public static <T> SuccessResponse<T> of(String message, HttpStatus status) {
        return new SuccessResponse<>(message, status);
    }

    public static <T> SuccessResponse<T> of(T data, String message) {
        return new SuccessResponse<>(data, message);
    }

    public static <T> SuccessResponse<T> of(String message) {
        return new SuccessResponse<>(message);
    }

    @Override public Object getData() { return data; }
}