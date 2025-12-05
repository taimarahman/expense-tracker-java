package com.project.expenseTracker.dto.response;

import org.springframework.http.HttpStatus;


public class ResponseSuccessData<T> extends ResponseBaseData<T>{

    public ResponseSuccessData(T data, String message, HttpStatus status) {
        super(data, message, status);
    }

    public ResponseSuccessData(String message, HttpStatus status) {
        super(message, status);
    }
}
