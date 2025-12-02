package com.project.expenseTracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSuccessData {
    private Boolean status;
    private Integer statusCode;
    private String message;
    private Object data;

    public ResponseSuccessData(Object data, String message, HttpStatus status) {
        this.status = true;
        this.statusCode = status.value();
        this.message = message;
        this.data = data;
    }
}
