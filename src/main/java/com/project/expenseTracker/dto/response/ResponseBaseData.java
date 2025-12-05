package com.project.expenseTracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ResponseBaseData<T> {
    private Boolean status;
    private Integer statusCode;
    private String message;
    private T data;

    public ResponseBaseData(T data, String message, HttpStatus status) {
        this.status = true;
        this.statusCode = status.value();
        this.message = message;
        this.data = data;
    }
    public ResponseBaseData(String message, HttpStatus status) {
        this.status = true;
        this.message = message;
        this.statusCode = status.value();
    }

}
